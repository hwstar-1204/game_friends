package com.capstone.game_friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    public String grantType;
    public String accessToken;
    public Long tokenExpiresIn;
}
