package com.capstone.game_friends.DTO;

import com.capstone.game_friends.Domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomProfileDTO {

    Long id;
    String nickname;
    String tier;
    String rank;
    String gameName;
    String tagLine;
    String profileImage;

    public static RandomProfileDTO of (Member member){
        return RandomProfileDTO.builder().
                id(member.getId()).
                nickname(member.getNickname()).
                profileImage(member.getProfileimage()).
                tier(member.getSummonerInfo().getTier()).
                rank(member.getSummonerInfo().getRank()).
                gameName(member.getSummonerInfo().getGameName()).
                tagLine(member.getSummonerInfo().getTagLine()).
                build();
    }
}
