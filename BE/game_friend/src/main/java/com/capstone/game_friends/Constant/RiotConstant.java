package com.capstone.game_friends.Constant;

import org.springframework.stereotype.Component;

@Component("RiotConstant")
public class RiotConstant {
    public static final String PUUID_REQUEST_URL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s/?api_key=%s";
    public static final String SUMMONER_REQUEST_URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/%s?api_key=%s";
    public static final String LEAGUE_REQUEST_URL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/%s/?api_key=%s";
    public static final String MATCH_REQUEST_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?startTime=%d&endTime=%d&type=%s&start=%d&count=%d&api_key=%s";
    public static final String MATCH_INFO_REQUEST_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/%s/?api_key=%s";
    public static final String ITEM_IMAGE_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/item/";
    public static final String SUMMONER_SPELL_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/spell/";
    public static final String SPELL_LIST_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/data/en_US/summoner.json";
    public static final String CHAMPION_LIST_REQUEST_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/data/en_US/champion.json";
    public static final String CHAMPION_IMAGE_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/champion/%s.png";
    public static final String CHAMPION_DETAIL_REQUEST_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/data/ko_KR/champion/%s.json";
    public static final String SPELL_IMAGE_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/spell/%s";
    public static final String PASSIVE_IMAGE_URL = "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/passive/%s";
}
