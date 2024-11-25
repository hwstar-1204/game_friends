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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SummonerService summonerService;
    private final String start = "0";
    private final String count = "10";
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

    public List<MatchResponseDTO> getMatchInfo(String matchList) {
        List<String> result = new ArrayList<>();
        List<MatchResponseDTO> matchResponseDTOList = new ArrayList<>();
        MatchInfoDTO matchInfoDTO = null;
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
                    System.out.println(response.getStatusLine().getStatusCode());
                    return null;
                }

                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);
                JsonNode node = objectMapper.readTree(jsonResponse);
                InfoDTO infoDTO = objectMapper.treeToValue(node.get("info"), InfoDTO.class);
                matchInfoDTO = objectMapper.treeToValue(node.get("info"), MatchInfoDTO.class);
                List<ParticipantDTO> test1 = infoDTO.getParticipants();

                for(int i = 0 ; i < test1.size() ; i++) {
                    playerStatsDTOList.add(new PlayerStatsDTO(test1.get(i)));
                }
                matchResponseDTOList.add(new MatchResponseDTO(playerStatsDTOList, matchInfoDTO));
            }
            return matchResponseDTOList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
