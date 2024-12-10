package com.capstone.game_friends.Service;

import com.capstone.game_friends.Constant.RiotConstant;
import com.capstone.game_friends.DTO.Riot.LeagueResponseDTO;
import com.capstone.game_friends.DTO.PuuIdRequestDTO;
import com.capstone.game_friends.DTO.PuuIdResponseDTO;
import com.capstone.game_friends.DTO.Riot.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Domain.Role;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Transactional
public class SummonerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MemberRepository memberRepository;

    @Value("${riot.api.key}")
    private String apiKey;

    // 계정 연동 시 개인 프로필 정보에 필요한 소환사 정보와 티어 획득 메서드
    public SummonerResponseDTO getSummoner(PuuIdRequestDTO requestDTO, Member member) {

        PuuIdResponseDTO puuId = getSummonerPuuId(requestDTO);  // 계정의 고유 PuuId 획득하는 메서드
        SummonerResponseDTO result = getSummonerInfo(requestDTO); // PuuId를 기반으로 소환사 정보 획득

        result.setSummonerPuuId(puuId);
        LeagueResponseDTO leagueInfo = getLeagueInfo(result.getSummonerId());
        result.setLeagueInfo(leagueInfo);

        member.setSummonerInfo(SummonerInfo.Info(result));
        member.setProfileimage(result.getProfileIconId());
        member.setRole(Role.ROLE_USER);

        memberRepository.save(member);

        return result;
    }

    // 암호화된 summonerId로 랭크 정보 가져오기
    public LeagueResponseDTO getLeagueInfo(String summonerId) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(LeagueInfoRequestUrl(summonerId));
            CloseableHttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode node = objectMapper.readTree(jsonResponse);
            return objectMapper.treeToValue(node.get(0), LeagueResponseDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("API 호출 실패", e);
        }
    }

    // 계정의 고유 PuuId 획득하는 메서드
    public PuuIdResponseDTO getSummonerPuuId(PuuIdRequestDTO requestDTO) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PuuIdRequestUrl(requestDTO));
            CloseableHttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return objectMapper.readValue(jsonResponse, PuuIdResponseDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // PuuId를 기반으로 소환사 정보 획득
    public SummonerResponseDTO getSummonerInfo(PuuIdRequestDTO requestDTO) {
        PuuIdResponseDTO puuIdResponseDTO = getSummonerPuuId(requestDTO);
        String puuid = puuIdResponseDTO.getPuuid();
        SummonerResponseDTO result;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(SummonerInfoRequestUrl(puuid));
            CloseableHttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JsonNode node = objectMapper.readTree(jsonResponse);
            String summonerId = node.get("id").asText();
            result = objectMapper.readValue(jsonResponse, SummonerResponseDTO.class);
            result.setProfileIconId(profileImageUrl(result.getProfileIconId()));
            result.setSummonerId(summonerId);
            return result;

        } catch (IOException e) {
            throw new RuntimeException("API 호출 실패", e);
        }
    }

    // PuuId를 기반으로 소환사 정보 얻기 위한 Url
    public String SummonerInfoRequestUrl(String puuid) {
        return String.format(RiotConstant.SUMMONER_REQUEST_URL,
                puuid,
                apiKey);
    }

    // 소환사 닉네임과 태그 라인으로 소환사 고유 PuuId 요청 Url
    public String PuuIdRequestUrl(PuuIdRequestDTO requestDTO) {
        String encodeGameName = URLEncoder.encode(requestDTO.getGameName(), StandardCharsets.UTF_8);
        return String.format(RiotConstant.PUUID_REQUEST_URL,
                encodeGameName,
                requestDTO.getTagLine(),
                apiKey);
    }

    // 암호화된 summonerId로 랭크 정보 요청 Url
    public String LeagueInfoRequestUrl(String summonerId) {
        return String.format(RiotConstant.LEAGUE_REQUEST_URL,
                summonerId,
                apiKey);
    }

    //
    public String profileImageUrl(String profileImageId) {
        return String.format(RiotConstant.PROFILE_IMAGE_URL, profileImageId);
    }
}