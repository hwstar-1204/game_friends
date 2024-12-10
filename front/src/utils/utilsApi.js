import apiClient from "./api";

const getUserInfo = async () => {  // 유저 정보 조회 (email, nickname)
    const response = await apiClient.get(`/member/me`);
    return response;
};

const getSummonerInfoByNickname = async (nickname) => {  // 닉네임으로 소환사 정보 조회 (gameName, tagLine)
    const response = await apiClient.get(`/member/summoner/name?nickname=${nickname}`);
    return response;
};

const getRandomUsersByNumber = async (number) => {  // 랜덤 유저 조회
    const response = await apiClient.get(`/user/random/${number}`);
    return response;
};

export { getUserInfo, getSummonerInfoByNickname, getRandomUsersByNumber };