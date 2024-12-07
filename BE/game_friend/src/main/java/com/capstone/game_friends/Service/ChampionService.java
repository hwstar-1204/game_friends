package com.capstone.game_friends.Service;

import com.capstone.game_friends.Constant.RiotConstant;
import com.capstone.game_friends.DTO.Riot.Champion.ChampionDTO;
import com.capstone.game_friends.DTO.Riot.Champion.ChampionInfoDTO;
import com.capstone.game_friends.DTO.Riot.Champion.SpellDTO;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChampionService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 챔피언 리스트 가져오기
    public List<ChampionDTO> getChampionList() {
        List<ChampionDTO> champions = new ArrayList<>();
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();

            HttpGet request = new HttpGet(RiotConstant.CHAMPION_LIST_REQUEST_URL);
            CloseableHttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode championList = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = championList.get("data");
            // 챔피언 이름 추출
            Iterator<String> fieldNames = dataNode.fieldNames();
            while (fieldNames.hasNext()) {
                String championKey = fieldNames.next();
                JsonNode champion = dataNode.get(championKey);

                String championName = champion.get("id").asText();
                String championImage = championImageUrl(championName);
                champions.add(new ChampionDTO(championName, championImage));
            }
            return champions;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ChampionInfoDTO getChampionInfo(String championName) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();

            HttpGet request = new HttpGet(championDetailUrl(championName));
            CloseableHttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode champion = objectMapper.readTree(jsonResponse);
            JsonNode data = champion.get("data");
            JsonNode firstField = data.fields().next().getValue();
            ChampionInfoDTO championInfoDTO = objectMapper.treeToValue(firstField, ChampionInfoDTO.class);
            for(SpellDTO s : championInfoDTO.getSpells()) {
                s.getImage().setFull("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/spell/"+ s.getImage().getFull());
            }
            return championInfoDTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String championImageUrl(String championName) {
        return String.format(RiotConstant.CHAMPION_IMAGE_URL, championName);
    }

    public String championDetailUrl(String championName) {
        return String.format(RiotConstant.CHAMPION_DETAIL_REQUEST_URL, championName);
    }
}
