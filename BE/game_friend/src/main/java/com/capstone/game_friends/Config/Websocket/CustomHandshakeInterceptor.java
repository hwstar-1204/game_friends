package com.capstone.game_friends.Config.Websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // 헤더 정보 로그 출력
        System.out.println("WebSocket Handshake Request Headers:");
        request.getHeaders().forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });

        return true; // 핸드쉐이크를 계속 진행
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {

    }
}
