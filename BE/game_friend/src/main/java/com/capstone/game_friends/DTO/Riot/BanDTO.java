package com.capstone.game_friends.DTO.Riot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BanDTO {
    private int championId;  // 밴된 챔피언 ID
    private int pickTurn;    // 밴 순서
}
