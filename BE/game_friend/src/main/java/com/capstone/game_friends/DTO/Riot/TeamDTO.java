package com.capstone.game_friends.DTO.Riot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDTO {
    private List<BanDTO> bans;         // 밴 정보 리스트
    private ObjectivesDTO objectives; // 팀 목표 정보
    private int teamId;               // 팀 ID
    private boolean win;              // 승리 여부
}
