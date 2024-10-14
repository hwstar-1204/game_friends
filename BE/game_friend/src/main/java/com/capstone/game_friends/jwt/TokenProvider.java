package com.capstone.game_friends.jwt;


import com.capstone.game_friends.DTO.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    // authorities_key, bearer_type 은 토큰을 생성하고 검증할 때 쓰이는 문자열
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //토큰 만료 시간
    private Key key; // jwt 만들 때 사용하는 암호화 키값

    public TokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    // 토큰 생성 메서드
    public TokenDTO generateTokenDto(Authentication authentication){
        // 매개변수 Authentication 통해 연재 사용자의 권한을 가져옴 ex) ROLE_USER, ROLE_GUEST, ROLE_ADMIN
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(tokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(tokenExpiresIn.getTime())
                .build();
    }
    // 토큰을 받았을 때 받은 토큰의 인증을 꺼내는 메서드
    // parseClaims 메서드로 String 형태 토큰을 claims 형태로 생성
    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);
        if(claims.get(AUTHORITIES_KEY)==null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                // AUTHORITIES_KEY 는 클레임에서 권한 정보를 식별하는 키로 클레임에서 권한 정보를 문자열로 가져온 후, 쉼표로 구분하여 배열로 변환합니다.
                // ROLE_USER,ROLE_ADMIN 이라면 ["ROLE_ADMIN" , "ROLE_USER"] 배열로 변환
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new) // 각 권한 문자열을 simpleGrantedAuthority 객체로 변환
                        .toList();

        //getSubject() 는 JWT 의 주체(사용자 이름) 정보를 가져옵니다.
        // UserDetails 는 Spring Security 에서 유저의 정보를 담는 인터페이스
        UserDetails principal = new User(claims.getSubject(), "",authorities);

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }
    // 토큰의 유효성을 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
    // 문자열 형태 accessToken 을 Claims 형태로 변환하는 메서드
    // 이를 통해 권한 정보의 유무 체크
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
