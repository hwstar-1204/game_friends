// template.js
import React, { useState, useEffect } from 'react';
import FriendList from '../components/friends/friends';
import '../components/friends/friends.css';
import './template.css';
import { useNavigate } from 'react-router-dom';

function Template({ children, friendsData }) {
  const [chatWindow, setChatWindow] = useState(null);
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    setIsLoggedIn(!!token);
  }, []);

  const handleChat = (friendName, friendTier) => {
    if (chatWindow && !chatWindow.closed) {
      chatWindow.location.href = `/chat?friend=${friendName}&tier=${friendTier}`;
      chatWindow.focus();
    } else {
      const newChatWindow = window.open(
        `/chat?friend=${friendName}&tier=${friendTier}`,
        '_blank',
        'width=400,height=600,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes'
      );
      setChatWindow(newChatWindow);
      newChatWindow.focus();
    }
  };

  const handleRecord = (friendName) => {
    navigate(`/record?friend=${friendName}`, { state: { friendsData } });
  };


  return (
    <div className="main-container">
      <header className="main-header">
        <div className="logo">
          <img src="/img/logo.png" onClick={() => window.location.href = '/'} alt="웹 페이지 로고" style={{ height: '60px', width: 'auto', cursor: 'pointer' }} />
          <h2>Random Game Friends</h2>
        </div>
        <div className="header-right">
          <button 
            className="profile-icon" 
            onClick={() => window.location.href = isLoggedIn ? '/profile' : '/login'}
          >
            {isLoggedIn ? '프로필' : '로그인'}
          </button>
        </div>
      </header>
      <div className="main-contents">
        {children}
        <div className="main-contents-right">
          <FriendList friendsData={friendsData} onChat={handleChat} onRecord={handleRecord} />
        </div>
      </div>
    </div>
  );
}

export default Template;