import apiClient from './api';

// API 함수들을 포함하는 객체 생성
const riotApi = {
    summonerApi: async (gameName, tagLine) => {
        const response = await apiClient.post('/riot/account', { gameName, tagLine });
        return response;
    },

    matchApi: async (nickname) => {
        const response = await apiClient.get(`/riot/match/history/friends?nickname=${nickname}`);
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