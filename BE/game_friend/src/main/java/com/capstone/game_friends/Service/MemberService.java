package com.capstone.game_friends.Service;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.DTO.Riot.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDTO getMyInfoBySecurity(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("인증 오류"));
    }

    public SummonerResponseDTO getSummoner() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("인증오류"));
        return new SummonerResponseDTO(member.getSummonerInfo());
    }

    public Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(()->new RuntimeException("존재하지 않는 닉네임"));
    }
}
