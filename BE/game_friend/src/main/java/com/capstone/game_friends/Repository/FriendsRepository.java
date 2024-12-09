package com.capstone.game_friends.Repository;

import com.capstone.game_friends.Domain.Friends;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Domain.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FriendsRepository extends JpaRepository<Friends,Long> {
    // 수락된 친구 목록 조회
    // 사용자가 보낸 친구 요청 중 수락된 목록과
    // 사용자가 받은 친구 요청 중 수락된 목록을 합쳐서 반환
    @Query("SELECT f.receiverUser FROM Friends f WHERE f.senderUser = :user AND f.status = 'accepted' " +
        "UNION " +
        "SELECT f.senderUser FROM Friends f WHERE f.receiverUser = :user AND f.status = 'accepted'")
    List<Member> findAcceptedFriendIdsByUserId(@Param("user") Member user);

    // 대기 중인 친구 요청 목록 조회
    // 사용자가 받은 친구 요청 중 대기 상태인 목록 반환
    @Query("SELECT f.senderUser FROM Friends f WHERE f.receiverUser = :user AND f.status = 'waiting'")
    List<Member> findWaitingFriendIdsByUserId(@Param("user") Member user);

    // 친구 관계 삭제
    // 양방향 친구 관계를 모두 삭제 (A->B, B->A)
    @Modifying
    @Query("DELETE FROM Friends r WHERE (r.senderUser.id = :senderUser AND r.receiverUser.id = :receiverUserId) OR (r.senderUser.id = :receiverUserId AND r.receiverUser.id = :senderUser)")
    void deleteRelationship(@Param("senderUser") Long senderUser, @Param("receiverUserId") Long receiverUserId);

    // 친구 요청 상태 업데이트
    // 친구 요청에 대한 수락/거절 상태 변경
    @Modifying
    @Transactional
    @Query("UPDATE Friends f SET f.status = :status WHERE f.senderUser.id = :senderUserId AND f.receiverUser.id = :receiverUserId")
    void updateStatus(@Param("senderUserId") Long senderUserId, @Param("receiverUserId") Long receiverUserId, @Param("status") Status status);

}
