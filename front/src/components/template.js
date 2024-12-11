// template.js
import React, { useState, useEffect } from 'react';
import FriendList from '../components/friends/friends';
import '../components/friends/friends.css';
import './template.css';
import { logout, changeNickname, changePassword } from '../utils/accontApi';
import { useNavigate } from 'react-router-dom';
import AccountChangeModal from '../components/modals/accountChange';
import FriendRequestModal from '../components/modals/friendRequests';
import LolConnectModal from '../components/modals/lolConnect';

function Template({ children, friendsData }) {
  const [chatWindow, setChatWindow] = useState(null);
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [sidebarVisible, setSidebarVisible] = useState(false);
  const [modalType, setModalType] = useState(null);
  const userNickname = localStorage.getItem('nickname');

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    setIsLoggedIn(!!token);
  }, []);

  const handleChat = (friendNickname, friendTier) => {
    if (chatWindow && !chatWindow.closed) {
      chatWindow.location.href = `/chat?friend=${friendNickname}&tier=${friendTier}`;
      chatWindow.focus();
    } else {
      const newChatWindow = window.open(
        `/chat?friend=${friendNickname}&tier=${friendTier}`,
        '_blank',
        'width=400,height=600,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes'
      );
      setChatWindow(newChatWindow);
      newChatWindow.focus();
    }
  };

  const handleRecord = (friendNickname) => {
    navigate(`/record?friend=${friendNickname}`, { state: { friendsData } });
  };

  const handleChampionInfo = () => {
    navigate('/champion', { state: { friendsData } });
  };

  const handleSidebarClose = () => {
    setSidebarVisible(false);
  };

  const handleModalSubmit = async (data) => {
    if (modalType === 'nickname') {
      const email = data.email;
      const nickname = data.nickname;
      
      try {
        const response = await changeNickname(email, nickname);
        console.log(response);
      } catch (error) {
        alert('닉네임 변경에 실패했습니다.');
      }

      alert(`닉네임이 "${nickname}"(으)로 변경되었습니다.`);
    
    } else if (modalType === 'password') {
      try {
        const response = await changePassword(data.email, data.prePassword, data.newPassword);
        console.log(response);
      } catch (error) {
        console.log(error);
      }
      alert('비밀번호가 변경되었습니다.');
    }
    setModalType(null);
  };

  const handleLogout = () => {
    logout();
    setIsLoggedIn(false);
    setSidebarVisible(false);
    navigate('/');
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
            className="champion-info-icon"
            onClick={handleChampionInfo}
          >
            챔피언 정보
          </button>

          <button 
            className="profile-icon" 
            onClick={() => {
              if (isLoggedIn) {
                setSidebarVisible(!sidebarVisible); // 사이드바 표시/숨김 토글
              } else {
                window.location.href = '/login'; // 로그인 페이지로 이동
              }
            }}
          >
            {isLoggedIn ? `${userNickname}` : '로그인'}
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
          <h3 className="sidebar-username">{isLoggedIn ? userNickname : '게스트'}</h3>
          <button 
            className="sidebar-button-nickname" 
            onClick={() => handleRecord(userNickname)} // () 안의 닉네임을 전적 검색으로 전달
          >
            내 계정
          </button>
          <button 
            className="sidebar-button-nickname" 
            onClick={() => setModalType('nickname')}
          >
            닉네임 변경
          </button>
          <button 
            className="sidebar-button-password" 
            onClick={() => setModalType('password')}
          >
            비밀번호 변경
          </button>
          <button 
            className="sidebar-button-request-friends"
            onClick={() => setModalType('request-friends')}
          >
            친구 요청 대기
          </button>
          <button 
            className="sidebar-button-lol-connect" 
            onClick={() => setModalType('lol-connect')}
          >
            계정 연동
          </button>

          <button className="sidebar-button-logout" onClick={handleLogout}>로그아웃</button>
          <button className="close-button" onClick={handleSidebarClose}>X</button>
        </div>
      )}
      {modalType === 'lol-connect' ? (
        <LolConnectModal
          onClose={() => setModalType(null)}
        />
      ) : modalType === 'request-friends' ? (
        <FriendRequestModal
          onClose={() => setModalType(null)}
        />
      ) : modalType && (
        <AccountChangeModal 
          type={modalType} 
          onClose={() => setModalType(null)} 
          onSubmit={handleModalSubmit} 
        />
      )}
    </div>
  );
}

export default Template;
