package com.capstone.game_friends.Service;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.DTO.*;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Repository.MemberRepository;
import com.capstone.game_friends.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResponseDTO signUp(MemberRequestDTO memberRequestDto){
        if(memberRepository.existsByEmail(memberRequestDto.getEmail())){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Member member = memberRequestDto.toMember(passwordEncoder);
        return  MemberResponseDTO.of(memberRepository.save(member));
    }

    public TokenDTO login(MemberRequestDTO requestDto){
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authentication);
    }

    // 비밀번호 변경 메서드
    public void changeMemberPassword(ChangePwdRequestDTO requestDTO){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()-> new RuntimeException("Error"));

        if(!member.getEmail().equals(requestDTO.getEmail())) {
            throw new RuntimeException("이메일이 일치하지 않습니다.");
        }

        if(!passwordEncoder.matches(requestDTO.getPrePassword(), member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        member.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        MemberResponseDTO.of(memberRepository.save(member));
    }

    // 닉네임 변경 메서드
    public void changeMemberNickname(ChangeNicknameDTO requestDTO) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()-> new RuntimeException("Error"));

        if(!member.getEmail().equals(requestDTO.getEmail())) {
            throw new RuntimeException("이메일이 일치하지 않습니다.");
        }

        member.setNickname(requestDTO.getNewNickname());
        MemberResponseDTO.of(member);
    }
}
