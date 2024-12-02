package com.capstone.game_friends.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// SecurityContext 에서 전역으로 유저 정보를 제공하는 유틸 클래스
// 요청이 들어오면 JwtFilter 의 doFilter 에 저장 되는데 거기에 있는 인증 정보를 꺼내 멤버의 ID를 반환함.
public class SecurityUtil {
    private SecurityUtil() { }

    public static Long getCurrentMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")){
            throw new RuntimeException("인증 정보가 존재하지 않습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}

