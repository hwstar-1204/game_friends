package com.capstone.game_friends.DTO.Riot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 필드를 무시
@Builder
public class MatchResponseDTO {
    private final List<PlayerStatsDTO> playerStatsDTO;
    private final MatchInfoDTO matchInfoDTO;

    public MatchResponseDTO(List<PlayerStatsDTO> playerStatsDTO, MatchInfoDTO matchInfoDTO) {
        this.playerStatsDTO = playerStatsDTO;
        this.matchInfoDTO = matchInfoDTO;
    }
}
