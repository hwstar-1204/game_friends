package com.capstone.game_friends.Controller;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.DTO.MemberRequestDTO;
import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/friends")
    public ResponseEntity<List<MemberResponseDTO>> getFriends(){
        return ResponseEntity.ok(customUserDetailsService.getFriends(SecurityUtil.getCurrentMemberId()));
    }

    @PostMapping("/friendrequest")
    @ResponseStatus(HttpStatus.OK)
    public void friendRequest(@RequestBody MemberRequestDTO memberRequestDTO){
        customUserDetailsService.friendRequest(SecurityUtil.getCurrentMemberId(), memberRequestDTO);
    }

}
