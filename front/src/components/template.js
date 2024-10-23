// template.js
import React, { useState } from 'react';
import FriendList from '../components/friends/friends';
import '../components/friends/friends.css';
import './template.css';

function Template({ children, friendsData }) {
  const [chatWindow, setChatWindow] = useState(null);

  const handleChat = (friendName) => {
    if (chatWindow && !chatWindow.closed) {
      chatWindow.location.href = `/chat?friend=${friendName}`;
    } else {
      const newChatWindow = window.open(
        `/chat?friend=${friendName}`,
        '_blank',
        'width=400,height=600,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes'
      );
      setChatWindow(newChatWindow);
    }
  };

  return (
    <div className="main-container">
      <header className="main-header">
        <div className="logo">
          <img src="/img/logo.png" onClick={() => window.location.href = '/'} alt="웹 페이지 로고" style={{ height: '40px', width: 'auto', cursor: 'pointer' }} />
        </div>
        <div className="header-right">
          <button className="profile-icon" onClick={() => window.location.href='/login'}>
            로그인
          </button>
        </div>
      </header>
      <div className="main-contents">
        {children}
        <div className="main-contents-right">
          <FriendList friendsData={friendsData} onChat={handleChat} />
        </div>
      </div>
    </div>
  );
}

export default Template;
