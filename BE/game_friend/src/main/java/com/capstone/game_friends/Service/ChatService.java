package com.capstone.game_friends.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class ChatService {
    private final AtomicLong chatRoomIdGenerator = new AtomicLong(1); // AtomicLong을 사용하여 ID 생성

    public Long createChatRoom() {
        Long createdChatRoomId = chatRoomIdGenerator.getAndIncrement();
        log.info("CreatedChatRoomID : {}", createdChatRoomId);
        return createdChatRoomId; // 고유한 채팅방 ID 생성
    }
}
