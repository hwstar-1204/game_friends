package com.capstone.game_friends.Controller;

import com.capstone.game_friends.DTO.ChangePwdRequestDTO;
import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.DTO.SummonerResponseDTO;
import com.capstone.game_friends.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<SummonerResponseDTO> getMember(){
        return ResponseEntity.ok(memberService.getSummonerInfo());
    }

    @PostMapping("/password")
    public ResponseEntity<MemberResponseDTO> setMemberPassword(@RequestBody ChangePwdRequestDTO requestDto) {
        return ResponseEntity.ok(memberService.changeMemberPassword(requestDto.getPrePassword(), requestDto.getNewPassword()));
    }
}
