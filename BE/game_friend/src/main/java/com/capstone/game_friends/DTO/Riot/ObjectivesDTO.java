package com.capstone.game_friends.DTO.Riot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectivesDTO {
    private ObjectiveDTO baron;       // 바론 목표
    private ObjectiveDTO champion;    // 챔피언 목표
    private ObjectiveDTO dragon;      // 드래곤 목표
    private ObjectiveDTO horde;       // 적 처치 목표
    private ObjectiveDTO inhibitor;   // 억제기 목표
    private ObjectiveDTO riftHerald;  // 협곡의 전령 목표
    private ObjectiveDTO tower;       // 포탑 목표
}