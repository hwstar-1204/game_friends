package com.capstone.game_friends.DTO.Riot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class MatchInfoDTO {
    private String endOfGameResult; // 게임 종료 결과
    private long gameCreation; // 게임 생성 시간 (Unix timestamp)
    private long gameDuration; // 게임 지속 시간
    private Long gameEndTimestamp; // 게임 종료 시간 (Unix timestamp)
    private long gameId; // 게임 ID
    private String gameMode; // 게임 모드
    private String gameName; // 게임 이름
    private long gameStartTimestamp; // 게임 시작 시간 (Unix timestamp)
    private String gameType; // 게임 타입
    private String gameVersion; // 게임 버전
    private int mapId; // 맵 ID
}
