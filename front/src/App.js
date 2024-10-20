import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './components/accounts/login';
import RegisterPage from './components/accounts/register';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        {/* 다른 경로들에 대한 라우팅도 여기에 추가할 수 있어요 */}
      </Routes>
    </Router>
  );
}

export default App;