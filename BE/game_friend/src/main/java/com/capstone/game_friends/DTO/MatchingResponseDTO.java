package com.capstone.game_friends.DTO;

import com.capstone.game_friends.Domain.Member;
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

    public static MatchingResponseDTO of(Member member, String chatRoomId){
        return MatchingResponseDTO.builder().
                memberId(member.getId()).
                chatRoomId(chatRoomId).
                gameName(member.getSummonerInfo().getGameName()).
                tagLine(member.getSummonerInfo().getTagLine()).
                tier(member.getSummonerInfo().getTier()).
                rank(member.getSummonerInfo().getRank()).
                build();
    }
}


