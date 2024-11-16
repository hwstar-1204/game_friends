package com.capstone.game_friends.Domain;

import com.capstone.game_friends.DTO.SummonerResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "summoner_info")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SummonerInfo {

    @Id
    @Column(name = "puuid")
    private String puuid;
    private String leagueId;
    private String queueType;
    private String tier;
    private String gameName;
    private String tagLine;
    private String summonerRank;
    private String summonerId;
    private long revisionDate;
    private long summonerLevel;
    private int profileIconId;
    private int leaguePoints;
    private int wins;
    private int losses;

    public static SummonerInfo Info(SummonerResponseDTO responseDTO) {
        return SummonerInfo.builder()
                .gameName(responseDTO.getGameName())
                .tagLine(responseDTO.getTagLine())
                .profileIconId(responseDTO.getProfileIconId())
                .revisionDate(responseDTO.getRevisionDate())
                .summonerId(responseDTO.getSummonerId())
                .puuid(responseDTO.getPuuid())
                .summonerLevel(responseDTO.getSummonerLevel())
                .leagueId(responseDTO.getLeagueId())
                .queueType(responseDTO.getQueueType())
                .tier(responseDTO.getTier())
                .summonerRank(responseDTO.getRank())
                .leaguePoints(responseDTO.getLeaguePoints())
                .wins(responseDTO.getWins())
                .losses(responseDTO.getLosses())
                .build();
    }
}

