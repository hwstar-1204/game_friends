package com.capstone.game_friends.Controller;

import com.capstone.game_friends.DTO.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/chattest")
    public String chatpage(){
        return "chattest";
    }

    @MessageMapping("/chat/message") //메세지 전송
    public void message(@RequestBody ChatMessageDTO chatMessageDTO) {
        log.info("채팅방 id: {}, 메세지: {}", chatMessageDTO.getChatroomid(), chatMessageDTO.getMessage());
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessageDTO.getChatroomid(),chatMessageDTO);
    }

}
