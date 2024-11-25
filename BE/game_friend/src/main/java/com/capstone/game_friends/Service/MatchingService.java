package com.capstone.game_friends.Service;

import com.capstone.game_friends.DTO.MatchingResponseDTO;
import com.capstone.game_friends.Domain.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchingService {
    private final List<Long> waitingList = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final int requiredPlayers = 2; // 매칭에 필요한 최소 인원 수
    private ChatService chatService;

    public synchronized void requestMatching(Long userId) {
        // 대기 리스트에 사용자 추가
        waitingList.add(userId);
        if (waitingList.size() == 1) matchUsers(); //처음 대기열에 들어왔을 시 매칭 시작
        System.out.println("사용자가 대기 리스트에 추가되었습니다: " + userId);
    }

    public synchronized void cancelMatching(Long userId) {
        if (waitingList.remove(userId)) {
            System.out.println("사용자가 대기 리스트에서 제거되었습니다: " + userId);
            // 매칭 취소 메시지 전송
            messagingTemplate.convertAndSend("/topic/match/cancel/" + userId, userId);
        } else {
            System.out.println("사용자가 대기 리스트에 존재하지 않습니다: " + userId);
        }
    }

    @Scheduled(fixedDelay = 1000) // 5초마다 매칭 시도
    public synchronized void matchUsers() {
        // 대기 중인 사용자 수가 충분한지 확인
        if (waitingList.size() < requiredPlayers) {
            System.out.println("현재 대기 중인 사용자 수가 부족합니다: ");
            return; // 대기자가 부족하면 메서드 종료
        }
        //while(waitingList.size() < requiredPlayers) System.out.println("매칭 대기 중"); //메서드가 시작되면 다른 사람이 들어올 때 까지 대기
        // 매칭 진행
        Collections.shuffle(waitingList); // 대기 리스트 섞기
        while (waitingList.size() >= requiredPlayers) {
            Long user1 = waitingList.remove(0); // 첫 번째 사용자
            Long user2 = waitingList.remove(0); // 두 번째 사용자

            Long chatroomid = chatService.createChatRoom();
            MatchingResponseDTO responseDTO1 = null;
            MatchingResponseDTO responseDTO2 = null;

            // 매칭 결과 전송
            messagingTemplate.convertAndSend("/topic/match/" + user1, responseDTO1.of(user2,chatroomid)); //나중에 채팅방 번호, 소환사 정보 추가
            messagingTemplate.convertAndSend("/topic/match/" + user2, responseDTO2.of(user1,chatroomid)); //나중에 채팅방 번호, 소환사 정보 추가
            System.out.println("매칭 성공: " + user1 + "와 " + user2 + "가 매칭되었습니다.");
        }
    }
}

