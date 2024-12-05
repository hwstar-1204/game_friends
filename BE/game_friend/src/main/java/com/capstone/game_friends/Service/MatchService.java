package com.capstone.game_friends.Service;

import com.capstone.game_friends.DTO.*;
import com.capstone.game_friends.DTO.Riot.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SummonerService summonerService;
    private final String start = "0";
    private final String count = "5";
    @Value("${riot.api.key}")
    private String apiKey;

    // 유저의 puuid를 기반으로 랭크게임 10개 가져오기
    public List<MatchResponseDTO> getMatchList(PuuIdRequestDTO requestDTO, String gameType) {
        PuuIdResponseDTO responseDTO = summonerService.getSummonerPuuId(requestDTO);

        String serverUrl = "https://asia.api.riotgames.com";
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();

            HttpGet request = new HttpGet(
                    serverUrl + "/lol/match/v5/matches/by-puuid/" + responseDTO.getPuuid() + "/ids?type=" + gameType + "&start=" + start + "&count=" + count + "&api_key=" + apiKey);
            CloseableHttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return getMatchInfo(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MatchResponseDTO> getMatchInfo(String matchList) throws IOException {
        List<String> result = new ArrayList<>();
        List<MatchResponseDTO> matchResponseDTOList = new ArrayList<>();
        MatchInfoDTO matchInfoDTO = null;
        Map<Integer, String> summonerSpells = getSummonerSpellMap();
        for (String part : matchList.split(",")) {
            result.add(part.replaceAll("[\\[\\]\"]", "").trim());
        }
        String serverUrl = "https://asia.api.riotgames.com";
        try {
            for (String match : result) {
                List<PlayerStatsDTO> playerStatsDTOList = new ArrayList<>();

                CloseableHttpClient client = HttpClientBuilder.create().build();

                HttpGet request = new HttpGet(
                        serverUrl + "/lol/match/v5/matches/" + match + "?api_key=" + apiKey);
                CloseableHttpResponse response = client.execute(request);

                if (response.getStatusLine().getStatusCode() != 200) {
                    return null;
                }

                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);
                JsonNode node = objectMapper.readTree(jsonResponse);
                InfoDTO infoDTO = objectMapper.treeToValue(node.get("info"), InfoDTO.class);
                matchInfoDTO = objectMapper.treeToValue(node.get("info"), MatchInfoDTO.class);
                List<ParticipantDTO> player = infoDTO.getParticipants();
                for(ParticipantDTO p : player) {
                    String summoner1Url = summonerSpells.get(p.getSummoner1Id());
                    String summoner2Url = summonerSpells.get(p.getSummoner2Id());
                    playerStatsDTOList.add(new PlayerStatsDTO(p, summoner1Url, summoner2Url));
                }
                matchResponseDTOList.add(new MatchResponseDTO(playerStatsDTOList, matchInfoDTO));
            }
            return matchResponseDTOList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 소환사 주문 이미지를 가져오기 위한 Map
    public Map<Integer, String> getSummonerSpellMap() throws IOException {
        Map<Integer, String> summonerSpells = new HashMap<>();
        String requestUrl = "https://ddragon.leagueoflegends.com/cdn/14.23.1/data/en_US/summoner.json";
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(requestUrl);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String jsonResponse = EntityUtils.toString(entity);
        JsonNode summonerSpell = objectMapper.readTree(jsonResponse);
        JsonNode data = summonerSpell.get("data");
        Iterator<String> fieldNames = data.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode spell = data.get(fieldName);
            int id = Integer.parseInt(spell.get("key").asText());
            JsonNode image = spell.get("image");
            String key = image.get("full").asText();
            summonerSpells.put(id, key);
        }
        for(int i =0; i<summonerSpells.size() ; i++) {
            System.out.println(summonerSpells.get(i));
        }
        return summonerSpells;
    }
}
