package com.capstone.game_friends.DTO;

import com.capstone.game_friends.Domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendResponseDTO {

    Long id;
    String nickname;
    String tier;
    String rank;
    String profileImage;

    public static FriendResponseDTO of (Member member){
        return FriendResponseDTO.builder().
                id(member.getId()).
                nickname(member.getNickname()).
                tier(member.getSummonerInfo().getTier()).
                rank(member.getSummonerInfo().getRank()).
                profileImage(member.getProfileimage()).
                build();
    }
}
