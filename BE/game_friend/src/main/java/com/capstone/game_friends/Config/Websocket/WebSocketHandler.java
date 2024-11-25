package com.capstone.game_friends.Config.Websocket;

import com.capstone.game_friends.Config.SecurityUtil;
import com.capstone.game_friends.Controller.MatchingController;
import com.capstone.game_friends.DTO.ChatMessageDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Service.ChatService;
import com.capstone.game_friends.Service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final ChatService chatService;

    // 소켓 세션을 저장할 Set
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // 채팅방 id와 소켓 세션을 저장할 Map
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    //매칭을 요청한 사용자를 저장할 Map {멤버 id, 세션}
    private final Map<Long,WebSocketSession> matchingRequestMap = new HashMap<>();

    //티어 정보 확인
    private MemberService memberService;

    // 소켓 연결 성공 시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub

//        String jwtToken = null;
//        jwtToken = getTokenFromSession(session);
//
//        log.info("Received JWT token: {}", jwtToken);
////        // JWT를 검증하고 처리하는 로직 추가

//        log.info("{} 연결됨", session.getId());
//        sessions.add(session);
//        SecurityUtil.getCurrentMemberId();
        //헤더에서 jwt받은 후 member id 가져와서 세션이랑 member id를 hashmap으로 묶어야됨

        //나중에 매칭 후 주어진 2개의 멤버 id를 이용해서 chatRoomSessionMap에 세션, 채팅방번호 저장하게 수정

        createChatRoomTest(session);
    }

    // 소켓 메세지 처리
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//
//        // 클라이언트로부터 받은 메세지를 ChatMessageDto로 변환
//        ChatMessageDTO chatMessageDto = mapper.readValue(payload, ChatMessageDTO.class);
//        log.info("session {}", chatMessageDto.toString());
//
//        // 메세지 타입에 따라 분기
//        if(chatMessageDto.getMessagetype().equals(ChatMessageDTO.MessageType.JOIN)){
//            // 입장 메세지
////            chatRoomSessionMap.computeIfAbsent(chatMessageDto.getChatroomid(), s -> new HashSet<>()).add(session);
////            chatMessageDto.setMessage("님이 입장하셨습니다."); //afterConnectionEstablished 로 이동하는게 맞는듯
//        }
//        else if(chatMessageDto.getMessagetype().equals(ChatMessageDTO.MessageType.LEAVE)){
//            // 퇴장 메세지
//            chatRoomSessionMap.get(chatMessageDto.getChatroomid()).remove(session);
//            chatMessageDto.setMessage("님이 퇴장하셨습니다.");
//        }
//
//        // 채팅 메세지 전송
//        for(WebSocketSession webSocketSession : chatRoomSessionMap.get(chatMessageDto.getChatroomid())){
//            webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsString(chatMessageDto)));
//        }
    }

    public void createChatRoom(Member member1, Member member2) throws Exception{
        WebSocketSession session1 = matchingRequestMap.get(member1.getId());
        WebSocketSession session2 = matchingRequestMap.get(member2.getId());
        Long chatroomid = chatService.createChatRoom();//채팅방 번호 생성
        String responseMessage = mapper.writeValueAsString(new ChatMessageDTO(chatroomid, "채팅방이 생성되었습니다."));
        session1.sendMessage(new TextMessage(responseMessage));
        session2.sendMessage(new TextMessage(responseMessage));
        chatRoomSessionMap.computeIfAbsent(chatroomid, s -> new HashSet<>()).add(session1); //채팅방 생성
        chatRoomSessionMap.computeIfAbsent(chatroomid, s -> new HashSet<>()).add(session2); //채팅방 생성
        session1.sendMessage(new TextMessage(member1.getNickname()+"님이 입장하셨습니다."));
        session2.sendMessage(new TextMessage(member2.getNickname()+"님이 입장하셨습니다."));
    }

    public  void createChatRoomTest(WebSocketSession session) throws Exception{

        Long chatroomid = chatService.createChatRoom();//채팅방 번호 생성
        String responseMessage = mapper.writeValueAsString(new ChatMessageDTO(chatroomid, "채팅방이 생성되었습니다."));
        session.sendMessage(new TextMessage(responseMessage));
        session.sendMessage(new TextMessage("WebSocket 연결 완료"));
        chatRoomSessionMap.computeIfAbsent(chatroomid, s -> new HashSet<>()).add(session); //채팅방 생성
        session.sendMessage(new TextMessage("님이 입장하셨습니다."));
    }

    // 소켓 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
        session.sendMessage(new TextMessage("WebSocket 연결 종료"));
    }

    private String getTokenFromSession(WebSocketSession session) throws Exception {
        // 쿼리 파라미터에서 JWT를 추출하는 로직 구현
        String query = session.getUri().getQuery();
        String token = null;
        //파라미터에서 토큰 추출
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair[0].equals("token")) {
                token = URLDecoder.decode(pair[1], "UTF-8");
            }
        }
        return token;
    }
}
