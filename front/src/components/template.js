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
  const [sidebarVisible, setSidebarVisible] = useState(false);

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

  const handleSidebarClose = () => {
    setSidebarVisible(false);
  };

  return (
    <div className={`main-container ${sidebarVisible ? 'sidebar-visible' : ''}`} onClick={(e) => sidebarVisible && e.currentTarget === e.target && setSidebarVisible(false)}>
      <header className="main-header">
        <div className="logo">
          <img src="/img/logo.png" onClick={() => window.location.href = '/'} alt="웹 페이지 로고" style={{ height: '60px', width: 'auto', cursor: 'pointer' }} />
          <h2>Random Game Friends</h2>
        </div>
        <div className="header-right">
          <button 
            className="profile-icon-test" 
            onClick={() => setSidebarVisible(!sidebarVisible)}
          >
            메뉴
          </button>

          <button 
            className="profile-icon" 
            onClick={() => window.location.href = isLoggedIn ? '/profile' : '/login'}
          >
            {isLoggedIn ? '프로필' : '로그인'}
          </button>
        </div>
      </header>
      <div className="main-contents" onClick={(e) => sidebarVisible && setSidebarVisible(false)}>
        {children}
        <div className="main-contents-right">
          <FriendList friendsData={friendsData} onChat={handleChat} onRecord={handleRecord} />
        </div>
      </div>
      {sidebarVisible && (
        <div className="sidebar" onClick={(e) => e.stopPropagation()}>
          <h3 className="sidebar-username">{isLoggedIn ? '내 닉네임' : '게스트'}</h3>
          <button className="sidebar-button-nickname" onClick={() => alert('닉네임 변경')}>닉네임 변경</button>
          <button className="sidebar-button-password" onClick={() => alert('비밀번호 변경')}>비밀번호 변경</button>
          <button className="sidebar-button-logout" onClick={() => { setIsLoggedIn(false); setSidebarVisible(false); }}>로그아웃</button>
          <button className="close-button" onClick={handleSidebarClose}>X</button>
        </div>
      )}
    </div>
  );
}

export default Template;
