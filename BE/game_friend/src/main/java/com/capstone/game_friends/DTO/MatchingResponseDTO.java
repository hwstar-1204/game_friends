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
    private String gameName;
    private String tagLine;
    private String tier;
    private String rank;
    private String chatRoomId;

    public static MatchingResponseDTO of(Long memberId,String chatRoomId){
        return MatchingResponseDTO.builder().memberId(memberId).chatRoomId(chatRoomId).build();
    }

    public static MatchingResponseDTO of(Long memberId, String chatRoomId, String gameName, String tagLine, String tier, String rank){
        return MatchingResponseDTO.builder().
                memberId(memberId).
                chatRoomId(chatRoomId).
                gameName(gameName).
                tagLine(tagLine).
                tier(tier).
                rank(rank).
                build();
    }
}


