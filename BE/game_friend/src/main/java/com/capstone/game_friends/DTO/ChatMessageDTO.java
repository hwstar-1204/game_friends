package com.capstone.game_friends.DTO;

import com.capstone.game_friends.Domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDTO {

//    public ChatMessageDTO(Long chatroomid, String s) {
//        this.messagetype = MessageType.JOIN;
//        this.chatroomid = chatroomid;
//        this.sender = "server";
//        this.message = s;
//
//    }
//
//    public enum MessageType{
//        JOIN, TALK, LEAVE
//    }

//    private MessageType  messagetype;
    private Long chatroomid;
//    private String sender;
    private String message;

}
