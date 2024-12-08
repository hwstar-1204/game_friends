import apiClient from './api';

const friendsApi = {
    // 친구 리스트 조회 
    getFriendsList: async () => {  
        const response = await apiClient.post('/user/friends');
        return response;
    },

    // 친구 요청 
    requestFriend: async (friendId) => {
        const response = await apiClient.post(`/user/friendrequest/${friendId}`);
        return response;
    },
    
    // 친구 요청 수락 
    acceptFriend: async (friendId) => {
        const response = await apiClient.post(`/user/acceptfriend/`, friendId);
        return response;
    },

    // 친구 요청 거절 
    rejectFriend: async (friendId) => {
        const response = await apiClient.post(`/user/declinedfriend/`, friendId);
        return response;
    },

    // 친구 삭제 
    deleteFriend: async (friendId) => {
        const response = await apiClient.post(`/user/deletefriend/`, friendId);
        return response;
    }
}


export default friendsApi;