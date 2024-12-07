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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(MatchListRequestUrl(responseDTO.getPuuid(), endTime, startTime, gameType));
            CloseableHttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return getMatchInfo(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MatchResponseDTO> getMatchInfo(String matchList) throws IOException {
        List<String> matchs = new ArrayList<>();
        List<MatchResponseDTO> matchResponseDTOList = new ArrayList<>();
        Map<Integer, String> summonerSpells = getSummonerSpellMap();

        for (String part : matchList.split(",")) {
            matchs.add(part.replaceAll("[\\[\\]\"]", "").trim());
        }

        try {
            for (String match : matchs) {
                List<PlayerStatsDTO> playerStatsDTOList = new ArrayList<>();

                CloseableHttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(MatchInfoRequestUrl(match));
                CloseableHttpResponse response = client.execute(request);

                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);
                JsonNode node = objectMapper.readTree(jsonResponse);
                InfoDTO infoDTO = objectMapper.treeToValue(node.get("info"), InfoDTO.class);
                MatchInfoDTO matchInfoDTO = objectMapper.treeToValue(node.get("info"), MatchInfoDTO.class);
                List<ParticipantDTO> player = infoDTO.getParticipants();

                for (ParticipantDTO p : player) {
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
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(RiotConstant.SPELL_LIST_URL);
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

        return summonerSpells;
    }

    public String MatchInfoRequestUrl(String matchId) {
        return String.format(RiotConstant.MATCH_INFO_REQUEST_URL,
                matchId,
                apiKey);
    }

    public String MatchListRequestUrl(String puuId, long startTime, long endTime,  String gameType) {
        return String.format(RiotConstant.MATCH_REQUEST_URL,
                puuId, startTime, endTime,
                gameType, 0, 10, apiKey);
    }
}
