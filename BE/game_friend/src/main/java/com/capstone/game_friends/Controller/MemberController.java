package com.capstone.game_friends.Controller;

import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.DTO.Riot.SummonerNameDTO;
import com.capstone.game_friends.DTO.Riot.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
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

    // DB에 있는 회원 닉네임으로 라이엇 게임이름, 태그 가져오기
    @GetMapping("/summoner/name")
    public ResponseEntity<SummonerNameDTO> getSummonerName(@RequestParam("nickname") String nickname) {
        Member member = memberService.findByNickname(nickname);
        return ResponseEntity.ok(new SummonerNameDTO(
                member.getSummonerInfo().getGameName(),
                member.getSummonerInfo().getTagLine()));
    }
}
