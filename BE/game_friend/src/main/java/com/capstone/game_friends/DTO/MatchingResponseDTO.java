package com.capstone.game_friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MatchingResponseDTO {
    private Long memberId;
    private Long chatRoomId;
    public static MatchingResponseDTO of(Long memberId, Long chatRoomId){
        return MatchingResponseDTO.builder().
                memberId(memberId).
                chatRoomId(chatRoomId).
                build();
    }
}


