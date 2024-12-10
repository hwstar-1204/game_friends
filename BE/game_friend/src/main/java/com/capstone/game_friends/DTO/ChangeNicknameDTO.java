package com.capstone.game_friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNicknameDTO {
    private String email;
    private String newNickname;
}
