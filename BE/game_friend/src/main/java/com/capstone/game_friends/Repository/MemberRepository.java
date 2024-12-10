package com.capstone.game_friends.Repository;


import com.capstone.game_friends.Domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM Member WHERE id <> :memberId AND puuid IS NOT NULL ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Member> findRandomEntitiesExcludingId(@Param("memberId") Long memberId, @Param("count") int count);

}
