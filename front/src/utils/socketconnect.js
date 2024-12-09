import apiClient from "./api"

const socketUtil = {
    getMyId: async () => {
        const response = await apiClient.get(`/beforematch`);
        return response;
    }
       
}

export default socketUtil;