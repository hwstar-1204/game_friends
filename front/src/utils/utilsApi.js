import apiClient from "./api";

const getSummonerInfoByNickname = async (nickname) => {  // 닉네임으로 소환사 정보 조회
    const response = await apiClient.get(`/member/summoner/name?nickname=${nickname}`);
    return response;
};

const getRandomUsersByNumber = async (number) => {  // 랜덤 유저 조회
    const response = await apiClient.get(`/user/random/${number}`);
    return response;
};

export { getSummonerInfoByNickname, getRandomUsersByNumber };