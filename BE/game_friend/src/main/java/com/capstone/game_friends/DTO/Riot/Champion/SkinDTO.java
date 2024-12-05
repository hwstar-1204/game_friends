package com.capstone.game_friends.DTO.Riot.Champion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// SkinDTO: 챔피언의 스킨 정보를 담는 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkinDTO {
    private String id;  // 스킨 ID (고유값)
    private int num;  // 스킨 번호 (기본 스킨은 0부터 시작)
    private String name;  // 스킨 이름
}

