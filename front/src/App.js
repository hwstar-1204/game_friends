// App.js

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './components/accounts/login';
import RegisterPage from './components/accounts/register';
import MainPage from './components/main';
import RecordPage from './components/record';
import ChatWindow from './components/friends/chat';
import ProfilePage from './components/profiles/profile';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/record" element={<RecordPage />} />
        <Route path="/profile" element={<ProfilePage />} />  {/* 프로필 페이지 추가 */}
        <Route path="/chat" element={<ChatWindow friendName={new URLSearchParams(window.location.search).get('friend') || '친구 이름 없음'} />} />
      </Routes>
    </Router>
  );
}

export default App;
