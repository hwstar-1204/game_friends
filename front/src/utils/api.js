import config from '../config';

const SERVER_URL = config.SERVER_URL;

// API 요청을 위한 기본 설정
const apiClient = {
  // GET 요청
  get: async (endpoint) => {
    const token = localStorage.getItem('accessToken');
    try {
      const response = await fetch(`${SERVER_URL}${endpoint}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          ...(token && { 'Authorization': `Bearer ${token}` })
        }
      });
      
      if (!response.ok) {
        throw new Error('API 요청 실패');
      }
      
      return await response.json();
    } catch (error) {
      console.error('API 오류:', error);
      throw error;
    }
  },

  // POST 요청
  post: async (endpoint, data) => {
    const token = localStorage.getItem('accessToken');
    try {
      const response = await fetch(`${SERVER_URL}${endpoint}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...(token && { 'Authorization': `Bearer ${token}` })
        },
        body: JSON.stringify(data)
      });

      if (!response.ok) {
        throw new Error('API 요청 실패');
      }

      const contentType = response.headers.get('content-type');
      if (contentType && contentType.includes('application/json')) {
        return await response.json();
      }
      return response;
    } catch (error) {
      console.error('API 오류:', error);
      throw error;
    }
  },

  // PUT 요청
  put: async (endpoint, data) => {
    const token = localStorage.getItem('accessToken');
    try {
      const response = await fetch(`${SERVER_URL}${endpoint}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          ...(token && { 'Authorization': `Bearer ${token}` })
        },
        body: JSON.stringify(data)
      });

      if (!response.ok) {
        throw new Error('API 요청 실패');
      }

      return await response.json();
    } catch (error) {
      console.error('API 오류:', error);
      throw error;
    }
  },

  // DELETE 요청
  delete: async (endpoint) => {
    const token = localStorage.getItem('accessToken');
    try {
      const response = await fetch(`${SERVER_URL}${endpoint}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          ...(token && { 'Authorization': `Bearer ${token}` })
        }
      });

      if (!response.ok) {
        throw new Error('API 요청 실패');
      }

      return await response.json();
    } catch (error) {
      console.error('API 오류:', error);
      throw error;
    }
  }
};

export default apiClient; 