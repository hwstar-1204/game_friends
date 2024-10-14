package com.capstone.game_friends.Service;

import com.capstone.game_friends.DTO.SummonerResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class SummonerService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.api.key}")
    private String apiKey;

    public String getUserPuuId(String gameName, String tagLine){

        SummonerResponseDTO result;

        String serverUrl = "https://asia.api.riotgames.com";

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/riot/account/v1/accounts/by-riot-id/" + gameName+"/"+ tagLine + "?api_key=" + apiKey);
            CloseableHttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            result = objectMapper.readValue(entity.getContent(), SummonerResponseDTO.class);
            return result.getPuuid();

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public SummonerResponseDTO getSummonerInfo(String puuId){
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
            result = objectMapper.readValue(entity.getContent(), SummonerResponseDTO.class);
            return result;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
