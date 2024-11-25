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
    public void message(@RequestBody ChatMessageDTO chatMessageDTO ) {
        log.info("chatRoomId: {}, message: {}", chatMessageDTO.getChatroomid(), chatMessageDTO.getMessage());
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessageDTO.getChatroomid(),chatMessageDTO);
    }

//    //입장을 할 때 사용하는 루트입니다.
//    @MessageMapping("/chat/enter")
//    public void enter(@RequestBody ChatRequestDto dto) {
//        messagingTemplate.convertAndSend("/sub/chat/room/1", dto);
//    }
//
//    //메세지를 전송할 때 사용하는 루트입니다.
//    @MessageMapping("/chat/message")
//    public void message(@RequestBody ChatRequestDto dto) {
//        messagingTemplate.convertAndSend("/sub/chat/room/1", dto);
//    }
}
