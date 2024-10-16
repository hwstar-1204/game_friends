package com.capstone.game_friends.DTO;

import com.capstone.game_friends.Domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDTO {
    private String email;
    private String nickname;

    public static MemberResponseDTO of(Member member){
        return MemberResponseDTO.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
