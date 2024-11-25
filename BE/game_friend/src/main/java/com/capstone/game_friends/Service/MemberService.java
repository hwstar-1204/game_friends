package com.capstone.game_friends.Service;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.DTO.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Domain.SummonerInfo;
import com.capstone.game_friends.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMyInfoBySecurity(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("인증 오류"));
    }

    public SummonerResponseDTO getSummonerInfo(){
        Member member = getMyInfoBySecurity();
        return member.getSummonerInfo();
    }

    @Transactional
    public MemberResponseDTO changeMemberPassword(String prePassword, String newPassword){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("Error"));
        if(!passwordEncoder.matches(prePassword, member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        member.setPassword(newPassword);
        return MemberResponseDTO.of(memberRepository.save(member));
    }
}
