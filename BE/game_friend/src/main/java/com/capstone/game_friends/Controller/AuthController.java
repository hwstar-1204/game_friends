package com.capstone.game_friends.Controller;

import com.capstone.game_friends.DTO.*;
import com.capstone.game_friends.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO requestDto){
        return ResponseEntity.ok(authService.login(requestDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO requestDto){
        return ResponseEntity.ok(authService.signUp(requestDto));
    }

    // 비밀번호 변경
    @PostMapping("/change/password")
    public ResponseEntity<MessageDTO> setMemberPassword(@RequestBody ChangePwdRequestDTO requestDto) {
        authService.changeMemberPassword(requestDto);
        return ResponseEntity.ok(new MessageDTO("비밀번호가 변경되었습니다."));
    }
    // 닉네임 변경
    @PostMapping("/change/nickname")
    public ResponseEntity<MessageDTO> setMemberNickname(@RequestBody ChangeNicknameDTO requestDTO) {
        authService.changeMemberNickname(requestDTO);
        return ResponseEntity.ok(new MessageDTO("닉네임이 변경되었습니다."));
    }
}
