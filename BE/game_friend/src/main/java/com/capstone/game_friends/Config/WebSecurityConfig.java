package com.capstone.game_friends.Config;

import com.capstone.game_friends.jwt.JwtAccessDeniedHandler;
import com.capstone.game_friends.jwt.JwtAuthenticationEntryPoint;
import com.capstone.game_friends.jwt.JwtFilter;
import com.capstone.game_friends.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public static final String[] PUBLIC_URLS = {
            // 공개 URL 목록
            "/ws/**"
    };

    private final String[] allowedOrigins = {"*"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        // csrf 설정
//        http
//                .csrf((auth)->auth.disable());
//        // 폼 로그인 형식 disable => POSTMAN 으로 검증
//        http
//                .formLogin((auth)->auth.disable());
//        // http basic 인증 박식 disable
//        http
//                .httpBasic((auth)->auth.disable());
//        // 경로별 인가 작업
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .anyRequest().permitAll()
//                );
//        // 인증되지 않은 사용자가 보호된 리소스에 접근하려고 할 때 처리방법
//        http.exceptionHandling((auth)->auth
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint));
//        // 인증된 사용자가 충분한 권한이 없을 때
//        http.exceptionHandling((auth)->auth
//                .accessDeniedHandler(jwtAccessDeniedHandler));
//        // 세션 설정
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        // 필터
//        http
//                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

        // CSRF 비활성화
        http.csrf(auth -> auth.disable());

        // 폼 로그인 및 HTTP Basic 인증 비활성화
        http.formLogin(auth -> auth.disable());
        http.httpBasic(auth -> auth.disable());

        // CORS 설정
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(Arrays.asList(allowedOrigins));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setMaxAge(3600L);
            configuration.setExposedHeaders(Collections.singletonList("Authorization"));
            return configuration;
        }));

        // 요청 권한 설정
        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/auth/login").permitAll() // 로그인 경로는 인증 없이 접근 허용
//                .anyRequest().authenticated()
//                .requestMatchers("/").permitAll()  // 공개 URL 허용
//                .anyRequest().authenticated()               // 나머지 요청은 인증 필요
                  .requestMatchers("/topic/**","/app/**").authenticated()
                  .anyRequest().permitAll()
        );

        // 예외 처리 설정
        http.exceptionHandling(auth -> {
            auth.authenticationEntryPoint(jwtAuthenticationEntryPoint);
            auth.accessDeniedHandler(jwtAccessDeniedHandler);
        });

        // 세션 관리 설정
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // JWT 필터 추가
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
