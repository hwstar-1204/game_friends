package com.capstone.game_friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PuuIdRequestDTO {
    private String gameName;
    private String tagLine;
}
