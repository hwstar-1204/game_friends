package com.capstone.game_friends.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ChatService {
    public String createChatRoom() { //채팅방 번호 생성
        String createdChatRoomId = UUID.randomUUID().toString();
        log.info("CreatedChatRoomID : {}", createdChatRoomId);
        return createdChatRoomId;
    }
}
