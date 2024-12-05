package com.capstone.game_friends.jwt;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
public class JwtChannelInterceptor implements ChannelInterceptor { //웹소켓 요청에 대한 jwt검증을 수행
    public static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_ = "Bearer" ;
    private final TokenProvider tokenProvider;

    public JwtChannelInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }

        Optional<String> jwtTokenOptional = Optional.ofNullable(accessor.getFirstNativeHeader(AUTHORIZATION));
        String jwtToken = jwtTokenOptional
                .filter(token -> token.startsWith(BEARER_))
                .map(token -> token.substring(BEARER_.length()))
                .filter(tokenProvider::validateToken)  // TokenProvider의 validateToken 사용
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        Authentication authentication = tokenProvider.getAuthentication(jwtToken);  // TokenProvider의 getAuthentication 사용
        accessor.setUser(authentication);

        return message;
    }


}
