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

    @Query("SELECT f.receiverUser FROM Friends f WHERE f.senderUser = :user AND f.status = 'accepted' " +
        "UNION " +
        "SELECT f.senderUser FROM Friends f WHERE f.receiverUser = :user AND f.status = 'accepted'")
        List<Member> findAcceptedFriendIdsByUserId(@Param("user") Member user);

    @Modifying
    @Query("DELETE FROM Friends r WHERE (r.senderUser.id = :senderUser AND r.receiverUser.id = :receiverUserId) OR (r.senderUser.id = :receiverUserId AND r.receiverUser.id = :senderUser)")
    void deleteRelationship(@Param("senderUser") Long senderUser, @Param("receiverUserId") Long receiverUserId);

    @Modifying
    @Transactional
    @Query("UPDATE Friends f SET f.status = :status WHERE f.senderUser.id = :senderUserId AND f.receiverUser.id = :receiverUserId")
    void updateStatus(@Param("senderUserId") Long senderUserId, @Param("receiverUserId") Long receiverUserId, @Param("status") Status status);

}
