package com.capstone.game_friends.DTO;

import com.capstone.game_friends.Domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDTO {

    private String chatroomid;
    private String sender;
    private String message;

}
