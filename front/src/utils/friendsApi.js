import apiClient from './api';

const friendsApi = {
    // 친구 리스트 조회 
    getFriendsList: async () => {  
        const response = await apiClient.post('/user/friends');
        return response;
    },

    // TODO : 친구 요청 
    requestFriend: async (friendId) => {
        const data = {
            "email" : "String",
            "password" : "String",
            "nickname" : "String"
    }
        const response = await apiClient.post('/user/friendrequest', data);
        return response;
    }
}


export default friendsApi;