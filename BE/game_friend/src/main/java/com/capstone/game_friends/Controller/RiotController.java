package com.capstone.game_friends.Controller;

import com.capstone.game_friends.DTO.SummonerRequestDTO;
import com.capstone.game_friends.DTO.SummonerResponseDTO;
import com.capstone.game_friends.Service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/riot")
@RequiredArgsConstructor
public class RiotController {
    private final SummonerService summonerService;

    @PostMapping("/account")
    public ResponseEntity<SummonerResponseDTO> getSummoner(@RequestBody SummonerRequestDTO requestDTO){
        String puuId = summonerService.getUserPuuId(requestDTO.getGameName(), requestDTO.getTagLine());
        System.out.println("puuID: "+ puuId);
        return ResponseEntity.ok(summonerService.getSummonerInfo(puuId));
    }

}
