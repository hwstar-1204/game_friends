package com.capstone.game_friends.Repository;


import com.capstone.game_friends.DTO.SummonerResponseDTO;
import com.capstone.game_friends.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}
