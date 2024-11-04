package com.capstone.game_friends.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 필드를 무시
public class SummonerResponseDTO {
    private long id;
    private int profileIconId;
    private long revisionDate;
    private String summonerId;
    private String puuId;
    private long summonerLevel;
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
}