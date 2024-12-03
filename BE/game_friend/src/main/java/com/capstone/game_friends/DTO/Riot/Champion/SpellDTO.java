package com.capstone.game_friends.DTO.Riot.Champion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// SpellDTO: 챔피언의 스킬 정보를 담는 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpellDTO {
    private String id;  // 스킬 ID (고유값)d
    private String name;  // 스킬 이름
    private String description;  // 스킬 설명 (스킬 효과에 대한 설명)
    private int maxrank;  // 스킬의 최대 레벨
    private List<Integer> cooldown;  // 스킬의 쿨타임 (초 단위) (리스트로 여러 값이 있을 수 있음)
    private String cooldownBurn;  // 쿨타임을 문자열로 표현한 값 (예: "10/9/8" 등의 형식)
    private List<Integer> cost;  // 스킬의 마나 소모 (리스트로 여러 값이 있을 수 있음)
    private String costBurn;  // 마나 소모를 문자열로 표현한 값 (예: "50/60/70" 등의 형식)
    private String costType;  // 스킬의 마나 소모 타입 (예: "mana" 등)
    private String rangeBurn;  // 사거리를 문자열로 표현한 값 (예: "800" 등의 형식)
    private ImageDTO image;  // 스킬 이미지 정보
    private String resource;  // 스킬 자원 유형 (예: "mana" 또는 "energy" 등)
}

