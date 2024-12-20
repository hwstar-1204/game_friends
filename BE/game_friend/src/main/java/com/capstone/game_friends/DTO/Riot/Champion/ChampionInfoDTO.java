package com.capstone.game_friends.DTO.Riot.Champion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


// 챔피언 정보 반환
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionInfoDTO {
    private String id; // 챔피언의 고유 식별자 (예: "Aatrox")
    private String key; // 챔피언의 고유 숫자 키 (예: "266")
    private String name; // 챔피언의 이름 (예: "아트록스")
    private String title; // 챔피언의 별칭 또는 직함 (예: "다르킨의 검")
    private ImageDTO image; // 챔피언의 이미지 정보 (sprite 및 크기 포함)
    private String lore; // 챔피언의 배경 이야기
    private String blurb; // 챔피언의 간단한 소개글
    private List<String> tags; // 챔피언의 역할 태그 (예: "Fighter", "Tank")
    private List<SpellDTO> spells; // 챔피언의 스킬 정보 (Q, W, E, R)
    private PassiveDTO passive; // 챔피언의 패시브 스킬 정보
}

