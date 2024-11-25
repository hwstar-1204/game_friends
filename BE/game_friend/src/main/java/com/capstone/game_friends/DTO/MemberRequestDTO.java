package com.capstone.game_friends.DTO;

import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

// 회원가입에 필요한 아이디와 비밀번호
public class MemberRequestDTO {
    private String email;
    private String password;

    public Member toMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password)) // 입력받은 비밀번호 DB에 암호화해서 저장
                .role(Role.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
