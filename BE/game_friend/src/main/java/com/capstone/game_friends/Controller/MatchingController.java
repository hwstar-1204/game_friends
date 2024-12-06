package com.capstone.game_friends.Controller;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.Service.MatchingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping("/matchingtest")
    public String matchingtest(){
        return "matchingtest";
    }

//    @MessageMapping("/match")
//    public void matchUser() {
//        //티어추가
//        matchingService.requestMatching(SecurityUtil.getCurrentMemberId()); // 매칭 요청 처리
//    }
    @GetMapping("/beforematch")
    public ResponseEntity<Long> getMemberid(){
        Long memberid = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(memberid);
    }

    @MessageMapping("/match")
    public void matchUser(@RequestBody Long id) {
        //티어추가
        matchingService.requestMatching(id); // 매칭 요청 처리
        matchingService.matchUsers();
    }
}
