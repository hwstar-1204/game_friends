package com.capstone.game_friends.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
// 계정 PuuId 얻었을 때 반환
public class PuuIdResponseDTO {
    private String gameName;
    private String tagLine;
    private String puuid;
}
