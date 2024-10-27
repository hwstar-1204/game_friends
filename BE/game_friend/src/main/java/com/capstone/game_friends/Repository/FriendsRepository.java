package com.capstone.game_friends.Repository;

import com.capstone.game_friends.Domain.Friends;
import com.capstone.game_friends.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
