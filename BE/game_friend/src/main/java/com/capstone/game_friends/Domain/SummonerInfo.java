package com.capstone.game_friends.Domain;

import com.capstone.game_friends.DTO.SummonerResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "summoner_info")
@Getter
@Setter
@Builder // 빌더 패턴 제공
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동 생성
public class SummonerInfo extends SummonerResponseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "puu_id")
    private String puuId;

    @Column(name = "profile_icon_id")
    private int profileIconId;

    @Column(name = "revision_date")
    private long revisionDate; // 데이터 마지막 조회시간

    @Column(name = "summoner_id")
    private String summonerId;

    @Column(name = "summoner_level")
    private long summonerLevel;

    @Column(name = "league_id")
    private String leagueId;

    @Column(name = "queue_type")
    private String queueType;

    @Column(name = "tier")
    private String tier;

    @Column(name = "summoner_rank")
    private String summonerRank;

    @Column(name = "league_points")
    private int leaguePoints;

    @Column(name = "wins")
    private int wins;

    @Column(name = "losses")
    private int losses;

    @Builder
    // 생성자
    public static SummonerInfo Info(SummonerResponseDTO responseDTO) {
        return SummonerInfo.builder()
                .profileIconId(responseDTO.getProfileIconId())
                .revisionDate(responseDTO.getRevisionDate())
                .summonerId(responseDTO.getSummonerId())
                .puuId(responseDTO.getPuuId())
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

