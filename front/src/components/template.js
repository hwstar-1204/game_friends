// template.js
import React, { useState, useEffect } from 'react';
import FriendList from '../components/friends/friends';
import '../components/friends/friends.css';
import './template.css';
import { useNavigate } from 'react-router-dom';
import AccountChangeModal from '../components/modals/accountChange';
import FriendRequestModal from '../components/modals/friendRequests';

function Template({ children, friendsData }) {
  const [chatWindow, setChatWindow] = useState(null);
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [sidebarVisible, setSidebarVisible] = useState(false);
  const [modalType, setModalType] = useState(null);

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

  const handleChampionInfo = () => {
    navigate('/champion', { state: { friendsData } });
  };

  const handleSidebarClose = () => {
    setSidebarVisible(false);
  };

  const handleModalSubmit = (value) => {
    if (modalType === 'nickname') {
      alert(`닉네임이 "${value}"(으)로 변경되었습니다.`);
    } else if (modalType === 'password') {
      alert('비밀번호가 변경되었습니다.');
    }
    setModalType(null);
  };

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    setIsLoggedIn(false);
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
            className="champion-icon"
            onClick={handleChampionInfo}
          >
            챔피언 정보
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
          <button 
            className="sidebar-button-nickname" 
            onClick={() => handleRecord('내 닉네임')} // () 안의 닉네임을 전적 검색으로 전달
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
          <button className="sidebar-button-logout" onClick={handleLogout}>로그아웃</button>
          <button className="close-button" onClick={handleSidebarClose}>X</button>
        </div>
      )}
      {modalType === 'request-friends' ? (
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
