import apiClient from './api';

// API 함수들을 포함하는 객체 생성
const riotApi = {
    summonerApi: async (gameName, tagLine) => {
        const response = await apiClient.post('/riot/account', { gameName, tagLine });
        return response;
    },

    matchApi: async (gameName, tagLine) => {
        const response = await apiClient.post(`/match/history`, { gameName, tagLine }); // 닉네임으로 변경 예정
        return response;
    },

    getChampionList: async () => {
        const response = await apiClient.get('/riot/champions');
        return response;
    },

    getChampionInfo: async (championName) => {
        const response = await apiClient.post(`/riot/champions/detail?championName=${championName}`);
        return response;
    }
};

export default riotApi;