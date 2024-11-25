package com.capstone.game_friends.Constant;

import org.springframework.stereotype.Component;

@Component("RiotConstant")
public class RiotConstant {
    public static final String KR_SERVER_URL = "https://kr.api.riotgames.com";
    public static final String ASIA_SERVER_URL = "https://asia.api.riotgames.com";
    public static final String PUUID_REQUEST_URL = "/riot/account/v1/accounts/by-riot-id/";
    public static final String SUMMONER_REQUEST_URL = "/lol/summoner/v4/summoners/by-puuid/";
    public static final String LEAGUE_REQUEST_URL = "/lol/league/v4/entries/by-summoner/";
    public static final String MATCH_REQUEST_URL = "/lol/match/v5/matches/by-puuid/";
}
