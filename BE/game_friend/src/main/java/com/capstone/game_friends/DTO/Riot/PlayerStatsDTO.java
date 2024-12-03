package com.capstone.game_friends.DTO.Riot;

import com.capstone.game_friends.Constant.RiotConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 필드를 무시
public class PlayerStatsDTO {
    // 아이템 리스트 이미지 Url 을 반환
    private final String item0;
    private final String item1;
    private final String item2;
    private final String item3;
    private final String item4;
    private final String item5;
    private final String item6;

    // KDA 관련
    private final int deaths;
    private final int kills;
    private final int assists;
    private final float kda;

    // 연속 킬 관련
    private final int pentaKills;
    private final int quadraKills;
    private final int tripleKills;

    // 챔프 정보
    private final String individualPosition;
    private final int champExperience;
    private final int champLevel;
    private final int championId;
    private final String championName;
    private final String teamPosition;
    private final String championImageUrl;

    // 마법 데미지 관련
    private final int magicDamageDealt;
    private final int magicDamageDealtToChampions;
    private final int magicDamageTaken;

    // 물리 데미지 관련
    private final int physicalDamageDealt;
    private final int physicalDamageDealtToChampions;
    private final int physicalDamageTaken;

    // 소환사 주문 이미지 Url 을 반환
    private final String summonerSpell1Url;
    private final String summonerSpell2Url;


    // 전체 딜량
    private final int totalDamageDealt;
    private final int totalDamageDealtToChampions;

    // 분당 데미지
    private final double damagePerMinute;

    // 전체 받은 피해량
    private final int totalDamageTaken;

    // 총 미니언 처치 수
    private final int totalMinionsKilled;

    // 플레이어 정보
    private final String puuid;
    private final String riotIdGameName;
    private final String riotIdTagline;
    private final String role;

    // 승리 여부
    private final boolean win;

    // 와드 관련
    private final int wardTakedowns;
    private final int visionScore;
    private final int wardsKilled;
    private final int wardsPlaced;

    public PlayerStatsDTO(ParticipantDTO participantDTO, String summoner1Url, String summoner2Url) {
        this.item0 = RiotConstant.ITEM_IMAGE_URL + participantDTO.getItem0() + ".png";
        this.item1 = RiotConstant.ITEM_IMAGE_URL + participantDTO.getItem1() + ".png";
        this.item2 = RiotConstant.ITEM_IMAGE_URL + participantDTO.getItem2() + ".png";
        this.item3 = RiotConstant.ITEM_IMAGE_URL + participantDTO.getItem3() + ".png";
        this.item4 = RiotConstant.ITEM_IMAGE_URL + participantDTO.getItem4() + ".png";
        this.item5 = RiotConstant.ITEM_IMAGE_URL + participantDTO.getItem5() + ".png";
        this.item6 = RiotConstant.ITEM_IMAGE_URL + participantDTO.getItem6() + ".png";
        this.summonerSpell1Url = RiotConstant.SUMMONER_SPELL_URL + summoner1Url;
        this.summonerSpell2Url = RiotConstant.SUMMONER_SPELL_URL + summoner2Url;
        this.deaths = participantDTO.getDeaths();
        this.kills = participantDTO.getKills();
        this.assists = participantDTO.getAssists();
        this.kda = participantDTO.getChallenges().getKda();
        this.pentaKills = participantDTO.getPentaKills();
        this.quadraKills = participantDTO.getQuadraKills();
        this.tripleKills = participantDTO.getTripleKills();
        this.individualPosition = participantDTO.getIndividualPosition();
        this.champExperience = participantDTO.getChampExperience();
        this.champLevel = participantDTO.getChampLevel();
        this.championId = participantDTO.getChampionId();
        this.championName = participantDTO.getChampionName();
        this.championImageUrl = RiotConstant.CHAMPION_IMAGE_URL + participantDTO.getChampionName() + ".png";
        this.teamPosition = participantDTO.getTeamPosition();
        this.magicDamageDealt = participantDTO.getMagicDamageDealt();
        this.magicDamageDealtToChampions = participantDTO.getMagicDamageDealtToChampions();
        this.magicDamageTaken = participantDTO.getMagicDamageTaken();
        this.physicalDamageDealt = participantDTO.getPhysicalDamageDealt();
        this.physicalDamageDealtToChampions = participantDTO.getPhysicalDamageDealtToChampions();
        this.physicalDamageTaken = participantDTO.getPhysicalDamageTaken();
        this.totalDamageDealt = participantDTO.getTotalDamageDealt();
        this.totalDamageDealtToChampions = participantDTO.getTotalDamageDealtToChampions();
        this.damagePerMinute = calculateDPM(participantDTO);
        this.totalDamageTaken = participantDTO.getTotalDamageTaken();
        this.totalMinionsKilled = participantDTO.getTotalMinionsKilled();
        this.puuid = participantDTO.getPuuid();
        this.riotIdGameName = participantDTO.getRiotIdGameName();
        this.riotIdTagline = participantDTO.getRiotIdTagline();
        this.role = participantDTO.getRole();
        this.win = participantDTO.isWin();
        this.wardTakedowns = participantDTO.getWardsKilled() + participantDTO.getWardsPlaced();
        this.visionScore = participantDTO.getVisionScore();
        this.wardsKilled = participantDTO.getWardsKilled();
        this.wardsPlaced = participantDTO.getWardsPlaced();
    }

    private double calculateDPM(ParticipantDTO participantDTO) {
        return participantDTO.getTotalDamageDealtToChampions() /
                (participantDTO.getTimePlayed() == 0 ? 1.0 : (participantDTO.getTimePlayed() / 60.0));
    }
}
