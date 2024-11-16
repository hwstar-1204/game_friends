package com.capstone.game_friends.DTO;

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
    private String gameName;  // puuId get
    private String tagLine; // puuId get
    private String puuid; // puuId get
    private String summonerId; // summoner get -> id를 summonerId로 변경해야함
    private long summonerLevel; // summoner get
    private int profileIconId; // summoner get
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

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