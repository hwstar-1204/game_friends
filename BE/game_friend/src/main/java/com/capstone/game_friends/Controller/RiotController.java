package com.capstone.game_friends.Controller;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.DTO.SummonerRequestDTO;
import com.capstone.game_friends.DTO.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Repository.MemberRepository;
import com.capstone.game_friends.Service.AuthService;
import com.capstone.game_friends.Service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riot")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class RiotController {
    private final SummonerService summonerService;
    private final MemberRepository memberRepository;
    // Riot 계정 연동
    @PostMapping("/account")
    public ResponseEntity<SummonerResponseDTO> getSummoner(@RequestBody SummonerRequestDTO requestDTO){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()-> new RuntimeException("로그인 후 이용 바랍니다."));
        return ResponseEntity.ok(summonerService.getSummonerInfo(requestDTO.getGameName(), requestDTO.getTagLine(), member));
    }

}
