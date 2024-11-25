package com.capstone.game_friends.Controller;

import com.capstone.game_friends.DTO.ChangePwdRequestDTO;
import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.DTO.Riot.SummonerResponseDTO;
import com.capstone.game_friends.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // 계정 연동 안 했을 때 정보 열람 시 이루어지는 컨트롤러 경로
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> getGuestMember(){
        return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    // 계정 연동 했을 때 정보 열람 시 이루어지는 컨트롤러 경로
    @GetMapping("/summoner/me")
    public ResponseEntity<SummonerResponseDTO> getSummonerMember() {
        return ResponseEntity.ok(memberService.getSummoner());
    }

    // 비밀번호 변경 ( 미완 )
    @PostMapping("/password")
    public ResponseEntity<MemberResponseDTO> setMemberPassword(@RequestBody ChangePwdRequestDTO requestDto) {
        return ResponseEntity.ok(memberService.changeMemberPassword(requestDto.getPrePassword(), requestDto.getNewPassword()));
    }
}
