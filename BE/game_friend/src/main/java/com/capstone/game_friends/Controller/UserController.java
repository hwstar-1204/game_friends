package com.capstone.game_friends.Controller;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.DTO.FriendResponseDTO;
import com.capstone.game_friends.DTO.MemberRequestDTO;
import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.Domain.Status;
import com.capstone.game_friends.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/friends")
    public ResponseEntity<List<FriendResponseDTO>> getFriends(){
        return ResponseEntity.ok(customUserDetailsService.getFriends(SecurityUtil.getCurrentMemberId()));
    }

//    @PostMapping("/friendrequest")
//    @ResponseStatus(HttpStatus.OK)
//    public void friendRequest(@RequestBody Map<String, Long> requestBody){
//        Long friendId = requestBody.get("friendId");
//        customUserDetailsService.friendRequest(SecurityUtil.getCurrentMemberId(), friendId);
//    }
    @PostMapping("/friendrequest/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void friendRequest(@PathVariable Long friendId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        customUserDetailsService.friendRequest(currentMemberId, friendId);
    }

    @PostMapping("/deletefriend")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@RequestBody Map<String, Long> requestBody) { //json { friendId : (ex)1  }
        Long friendId = requestBody.get("friendId");
        customUserDetailsService.deleteFriend(SecurityUtil.getCurrentMemberId(), friendId);
    }

    @PostMapping("/acceptfriend")
    @ResponseStatus(HttpStatus.OK)
    public void acceptFriend(@RequestBody Map<String, Long> requestBody){
        Long senderId = requestBody.get("friendId");
        customUserDetailsService.friendStatus(SecurityUtil.getCurrentMemberId(),senderId, Status.accepted);
    }

    @PostMapping("/declinedfriend")
    @ResponseStatus(HttpStatus.OK)
    public void declinedFriend(@RequestBody Map<String, Long> requestBody){
        Long senderId = requestBody.get("friendId");
        customUserDetailsService.friendStatus(SecurityUtil.getCurrentMemberId(),senderId,Status.declined);
    }

}
