package com.capstone.game_friends.Service;

import com.capstone.game_friends.DTO.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Domain.SummonerInfo;
import com.capstone.game_friends.Repository.MemberRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
public class SummonerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MemberRepository memberRepository;

    @Value("${riot.api.key}")
    private String apiKey;

    public String getPuuId(String gameName, String tagLine){

        String serverUrl = "https://asia.api.riotgames.com";

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/riot/account/v1/accounts/by-riot-id/" + gameName+"/"+ tagLine + "?api_key=" + apiKey);
            CloseableHttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("라이엇 API 호출 실패");
            }

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode node = objectMapper.readTree(jsonResponse);
            return node.get("puuid").asText();

        } catch (IOException e){
            throw new RuntimeException("API 호출 실패", e);
        }
    }

    public SummonerResponseDTO getSummonerId(String puuId){

        SummonerResponseDTO result;

        String serverUrl = "https://kr.api.riotgames.com";

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/summoner/v4/summoners/by-puuid/" + puuId + "?api_key=" + apiKey);
            CloseableHttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("라이엇 API 호출 실패");
            }

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            result = objectMapper.readValue(jsonResponse,SummonerResponseDTO.class);
            return result;

        } catch (IOException e){
            throw new RuntimeException("API 호출 실패", e);
        }
    }

    public SummonerResponseDTO getSummonerInfo(String gameName, String tagLine, Member member){

        String puuId = getPuuId(gameName, tagLine);
        if (puuId == null) {
            throw new RuntimeException("소환사를 찾을 수 없습니다.");
        }
        
        SummonerResponseDTO result = getSummonerId(puuId);
        if (result == null) {
            throw new RuntimeException("소환사 정보를 가져올 수 없습니다.");
        }

        String serverUrl = "https://kr.api.riotgames.com";

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/league/v4/entries/by-summoner/" + result.getId() + "?api_key=" + apiKey);
            CloseableHttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("라이엇 API 호출 실패");
            }

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode node = objectMapper.readTree(jsonResponse);
            
            if (node.isEmpty()) {  // 소환사 리그 정보가 없는 경우
                result.setPuuId(puuId);
                member.setSummonerInfo(SummonerInfo.Info(result));
                memberRepository.save(member);
                return result;
            }

            SummonerResponseDTO leagueInfo = objectMapper.treeToValue(node.get(0), SummonerResponseDTO.class);

            result.setPuuId(puuId);
            result.setLeagueId(leagueInfo.getLeagueId());
            result.setQueueType(leagueInfo.getQueueType());
            result.setTier(leagueInfo.getTier());
            result.setRank(leagueInfo.getRank());
            result.setLeaguePoints(leagueInfo.getLeaguePoints());
            result.setWins(leagueInfo.getWins());
            result.setLosses(leagueInfo.getLosses());

            member.setSummonerInfo(SummonerInfo.Info(result));
            memberRepository.save(member);

            return result;

        } catch (IOException e){
            throw new RuntimeException("리그 정보를 가져오는데 실패했습니다.", e);
        }
    }
}