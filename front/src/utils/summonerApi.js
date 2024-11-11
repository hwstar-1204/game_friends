import apiClient from './api';


const summonerApi = {
    getSummonerInfo: async (gameName, tagLine) => {
        const response = await apiClient.post('/riot/account', { gameName, tagLine });
        return response;
    }
}



export default summonerApi;