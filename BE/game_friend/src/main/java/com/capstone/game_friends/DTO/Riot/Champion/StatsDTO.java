package com.capstone.game_friends.DTO.Riot.Champion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// StatsDTO: 챔피언의 스탯 (HP, 공격력, 방어력 등) 정보를 담는 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatsDTO {
    private int hp;  // 챔피언의 기본 HP (체력)
    private int hpperlevel;  // 레벨당 HP 증가량
    private int mp;  // 챔피언의 기본 MP (마나)
    private int mpperlevel;  // 레벨당 MP 증가량
    private int movespeed;  // 챔피언의 이동 속도
    private int armor;  // 챔피언의 기본 방어력
    private double armorperlevel;  // 레벨당 방어력 증가량
    private int spellblock;  // 챔피언의 기본 마법 저항력
    private double spellblockperlevel;  // 레벨당 마법 저항력 증가량
    private int attackrange;  // 챔피언의 기본 공격 범위
    private double hpregen;  // 기본 HP 회복량 (초당)
    private double hpregenperlevel;  // 레벨당 HP 회복량 증가량
    private int mpregen;  // 기본 MP 회복량 (초당)
    private int mpregenperlevel;  // 레벨당 MP 회복량 증가량
    private int crit;  // 기본 치명타 확률
    private int critperlevel;  // 레벨당 치명타 확률 증가량
    private int attackdamage;  // 기본 공격력
    private int attackdamageperlevel;  // 레벨당 공격력 증가량
    private double attackspeedperlevel;  // 레벨당 공격속도 증가량
    private double attackspeed;  // 기본 공격속도
}

