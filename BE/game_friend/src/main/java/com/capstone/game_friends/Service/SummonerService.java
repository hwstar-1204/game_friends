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
                return null;
            }

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode node = objectMapper.readTree(jsonResponse);
            return node.get("puuid").asText();

        } catch (IOException e){
            e.printStackTrace();
            return null;
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
                return null;
            }

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            result = objectMapper.readValue(jsonResponse,SummonerResponseDTO.class);
            return result;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public SummonerResponseDTO getSummonerInfo(String gameName, String tagLine, Member member){

        String puuId = getPuuId(gameName, tagLine);
        SummonerResponseDTO result = getSummonerId(puuId);

        String serverUrl = "https://kr.api.riotgames.com";

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/league/v4/entries/by-summoner/" + result.getId() + "?api_key=" + apiKey);
            CloseableHttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode node = objectMapper.readTree(jsonResponse);
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
            e.printStackTrace();
            return null;
        }
    }
}