package com.capstone.game_friends.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 필드를 무시
public class SummonerResponseDTO {
    private int profileIconId; // 1번
    private long revisionDate; // 1번
    private String id; // 1번
    private String puuId; // 1번
    private long summonerLevel; // 1번
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
}