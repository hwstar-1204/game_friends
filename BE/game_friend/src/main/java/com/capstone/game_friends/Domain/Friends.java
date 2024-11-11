package com.capstone.game_friends.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate
    @Column
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member senderUser; // 친구 요청을 보낸 사용자

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Member receiverUser; // 친구가 되는 사용자

}
