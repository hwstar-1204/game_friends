import apiClient from './api';


const summonerApi = async (gameName, tagLine) => {
    const response = await apiClient.post('/riot/account', { gameName, tagLine });
    return response;
}

const matchApi = async (puuid) => {
    const response = await apiClient.post(`/match/history`, { puuid }); // 닉네임으로 변경 예정
    return response;
}

const getChampionList = async () => {
    const response = await apiClient.get('/riot/champions');
    return response;
}

const getChampionInfo = async (championName) => {
    const response = await apiClient.get(`/riot/champion?championName=${championName}`);
    return response;
}


export { summonerApi, matchApi, getChampionList, getChampionInfo };