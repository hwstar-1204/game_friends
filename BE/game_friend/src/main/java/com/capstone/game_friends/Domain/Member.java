package com.capstone.game_friends.Domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity // JPA 엔티티 임을 선언
@Getter
@Setter
@Builder // 빌더 패턴 제공
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동 생성
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING) // Enum 값이 DB에서 문자열 형태로 저장되게 함.
    private Role role;

    // SummonerInfo와 1:1 관계 설정
    @OneToOne(cascade = CascadeType.ALL) // CascadeType 설정
    @JoinColumn(name = "puuId") // 외래 키 설정
    private SummonerInfo summonerInfo;

    @CreatedDate
    @Column
    private LocalDateTime createAt;

    public Member(Long id, String email, String password, String nickname, LocalDateTime createAt, Role role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.createAt = createAt;
    }
}
