package com.capstone.game_friends.Controller;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.DTO.MessageDTO;
import com.capstone.game_friends.DTO.PuuIdRequestDTO;
import com.capstone.game_friends.DTO.Riot.MatchResponseDTO;
import com.capstone.game_friends.DTO.Riot.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Repository.MemberRepository;
import com.capstone.game_friends.Service.MatchService;
import com.capstone.game_friends.Service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/riot")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class RiotController {
    private final SummonerService summonerService;
    private final MemberRepository memberRepository;
    private final MatchService matchService;

    // Riot 계정 연동
    @PostMapping("/account")
    public ResponseEntity<SummonerResponseDTO> getSummoner(@RequestBody PuuIdRequestDTO requestDTO){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()-> new RuntimeException("로그인 후 이용 바랍니다."));
        return ResponseEntity.ok(summonerService.getSummoner(requestDTO, member));
    }

    @GetMapping("/match/history")
    public ResponseEntity<List<MatchResponseDTO>> test(@RequestBody PuuIdRequestDTO requestDTO) {
        String str = "ranked";
        return ResponseEntity.ok(matchService.getMatchList(requestDTO, str));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<MessageDTO> deleteSummoner(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(new MessageDTO("계정 연동 해제"));
    }
}
