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

    @GetMapping("/friends") // 친구 조회 get요청으로 수정
    public ResponseEntity<List<FriendResponseDTO>> getFriends(){
        return ResponseEntity.ok(customUserDetailsService.getFriends(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/friendswaiting") //대기 중인 친구 조회
    public ResponseEntity<List<FriendResponseDTO>> getfriendswaiting(){
        return ResponseEntity.ok(customUserDetailsService.getWaitingFriends(SecurityUtil.getCurrentMemberId()));
    }

    @PostMapping("/friendrequest/{friendId}") //친구요청
    @ResponseStatus(HttpStatus.OK)
    public void friendRequest(@PathVariable Long friendId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        customUserDetailsService.friendRequest(currentMemberId, friendId);
    }

    @PostMapping("/deletefriend") //친구 삭제
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@RequestBody Map<String, Long> requestBody) { //json { friendId : (ex)1  }
        Long friendId = requestBody.get("friendId");
        customUserDetailsService.deleteFriend(SecurityUtil.getCurrentMemberId(), friendId);
    }

    @PostMapping("/acceptfriend") //친구 수락
    @ResponseStatus(HttpStatus.OK)
    public void acceptFriend(@RequestBody Map<String, Long> requestBody){
        Long senderId = requestBody.get("friendId");
        customUserDetailsService.friendStatus(SecurityUtil.getCurrentMemberId(),senderId, Status.accepted);
    }

    @PostMapping("/declinedfriend") //친구 거절
    @ResponseStatus(HttpStatus.OK)
    public void declinedFriend(@RequestBody Map<String, Long> requestBody){
        Long senderId = requestBody.get("friendId");
        customUserDetailsService.friendStatus(SecurityUtil.getCurrentMemberId(),senderId,Status.declined);
    }

}
