package com.capstone.game_friends.DTO.Riot.Champion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ImageDTO: 챔피언 이미지 정보를 담는 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDTO {
    private String full;  // 이미지의 전체 파일 경로
    private String sprite;  // 스프라이트 이미지 파일 경로
    private String group;  // 이미지가 속한 그룹 (예: 'champion', 'spell' 등)
    private int x;  // 이미지의 x 좌표 (스프라이트에서의 위치)
    private int y;  // 이미지의 y 좌표 (스프라이트에서의 위치)
    private int w;  // 이미지의 너비
    private int h;  // 이미지의 높이
}

