package com.capstone.game_friends.DTO.Riot;

import com.capstone.game_friends.DTO.PuuIdResponseDTO;
import com.capstone.game_friends.Domain.SummonerInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 필드를 무시
@Builder

// 프로필에 표시될 소환사 랭크와 정보들
public class SummonerResponseDTO {
    private long revisionDate;
    private String gameName;
    private String tagLine;
    private String puuid;
    private String summonerId;
    private long summonerLevel;
    private String profileIconId;
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

    // SummonerInfo 객체를 매개변수로 받는 생성자
    public SummonerResponseDTO(SummonerInfo summonerInfo) {
        this.revisionDate = summonerInfo.getRevisionDate();
        this.gameName = summonerInfo.getGameName();
        this.tagLine = summonerInfo.getTagLine();
        this.puuid = summonerInfo.getPuuid();
        this.summonerId = summonerInfo.getSummonerId();
        this.summonerLevel = summonerInfo.getSummonerLevel();
        this.profileIconId = summonerInfo.getProfileIconId();
        this.leagueId = summonerInfo.getLeagueId();
        this.queueType = summonerInfo.getQueueType();
        this.tier = summonerInfo.getTier();
        this.rank = summonerInfo.getRank();
        this.leaguePoints = summonerInfo.getLeaguePoints();
        this.wins = summonerInfo.getWins();
        this.losses = summonerInfo.getLosses();
    }
    public void setLeagueInfo(LeagueResponseDTO leagueResponseDTO) {
        this.leaguePoints = leagueResponseDTO.getLeaguePoints();
        this.queueType = leagueResponseDTO.getQueueType();
        this.tier = leagueResponseDTO.getTier();
        this.losses = leagueResponseDTO.getLosses();
        this.wins = leagueResponseDTO.getWins();
        this.rank = leagueResponseDTO.getRank();
        this.leagueId = leagueResponseDTO.getLeagueId();
    }


    public void setSummonerPuuId(PuuIdResponseDTO responseDTO) {
        this.gameName = responseDTO.getGameName();
        this.tagLine = responseDTO.getTagLine();
        this.puuid = responseDTO.getPuuid();
    }
}