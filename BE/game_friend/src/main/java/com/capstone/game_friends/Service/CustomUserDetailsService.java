package com.capstone.game_friends.Service;

import com.capstone.game_friends.DTO.MemberRequestDTO;
import com.capstone.game_friends.DTO.MemberResponseDTO;
import com.capstone.game_friends.Domain.Friends;
import com.capstone.game_friends.Domain.Member;
import com.capstone.game_friends.Domain.Status;
import com.capstone.game_friends.Repository.FriendsRepository;
import com.capstone.game_friends.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final FriendsRepository friendsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자 입니다."));
    }

    private UserDetails createUserDetails(Member member){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());

        return new User(
                String.valueOf(member.getId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    public List<MemberResponseDTO> getFriends(Long userId){

        Member member = memberRepository.findById(userId).orElseThrow();

        return friendsRepository.findAcceptedFriendIdsByUserId(member).stream()
                .map(MemberResponseDTO::of)
                .collect(Collectors.toList());

    }

    public void friendRequest(Long userId, MemberRequestDTO memberRequestDTO){
        Friends friends = new Friends();

        friends.setSenderUser(memberRepository.findById(userId).orElseThrow());
        friends.setReceiverUser(memberRepository.findByEmail(memberRequestDTO.getEmail()).orElseThrow());
        friends.setStatus(Status.waiting);

        friendsRepository.save(friends);

    }
}
