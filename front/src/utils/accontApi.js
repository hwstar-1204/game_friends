import apiClient from "./api";

const register = async (email, password, nickname, role) => {
    const response = await apiClient.post('/auth/signup', { email, password, nickname, role });
    return response;
  };

const login = async (email, password) => {
  const response = await apiClient.post('/auth/login', { email, password });
  return response;
};

const logout = async () => {
//   const response = await apiClient.post('/auth/logout');
    localStorage.clear();
//   return response;
};

export { register, login, logout };

