package com.capstone.game_friends.Service;

import com.capstone.game_friends.DTO.MatchingResponseDTO;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
public class MatchingService {
    private final List<Long> waitingList = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final int requiredPlayers = 2; // 매칭에 필요한 최소 인원 수
    private ChatService chatService;
    private MemberRepository memberRepository;
    private final ReentrantLock lock = new ReentrantLock(); // 락 객체

    public synchronized void requestMatching(Long userId) {
        // 대기 리스트에 사용자 추가
        waitingList.add(userId);
        System.out.println("사용자가 대기 리스트에 추가되었습니다: " + userId);
    }

    public synchronized void cancelMatching(Long userId) {
        if (waitingList.remove(userId)) {
            System.out.println("사용자가 대기 리스트에서 제거되었습니다: " + userId);
            // 매칭 취소 메시지 전송
            messagingTemplate.convertAndSend("/topic/match/" + userId, userId);
        } else {
            System.out.println("사용자가 대기 리스트에 존재하지 않습니다: " + userId);
        }
    }

    @Scheduled(fixedDelay = 1000) // 1초마다 매칭 시도
    public synchronized void matchUsers() {
        // 대기 중인 사용자 수가 충분한지 확인
        if (waitingList.size() < requiredPlayers) {
            System.out.println("현재 대기 중인 사용자 수가 부족합니다: ");
            return; // 대기자가 부족하면 메서드 종료
        }

        // 매칭 진행
        Collections.shuffle(waitingList); // 대기 리스트 섞기
        while (waitingList.size() >= requiredPlayers) {
            Long user1 = waitingList.remove(0); // 첫 번째 사용자
            Long user2 = waitingList.remove(0); // 두 번째 사용자

            String chatroomid = chatService.createChatRoom();

            Member member1 = memberRepository.findById(user1).orElseThrow();
            Member member2 = memberRepository.findById(user2).orElseThrow();

//            messagingTemplate.convertAndSend("/topic/match/" + user1, responseDTO1.of(user1,chatroomid)); //test
//            messagingTemplate.convertAndSend("/topic/match/" + user2, responseDTO2.of(user2,chatroomid));

            // 매칭 결과 전송
            messagingTemplate.convertAndSend("/topic/match/" + user1, MatchingResponseDTO.of(member1,chatroomid));
            messagingTemplate.convertAndSend("/topic/match/" + user2, MatchingResponseDTO.of(member2,chatroomid));
            System.out.println("매칭 성공: " + user1 + "와 " + user2 + "가 매칭되었습니다.");
        }
    }
}

