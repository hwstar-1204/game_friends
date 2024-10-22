import React, { useState } from 'react';
import './main.css';

function MainPage() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState('');

  const handleProfileClick = () => {
    if (isLoggedIn) {
      // 로그아웃 또는 프로필 페이지로 이동하는 로직 추가 가능
    } else {
      window.location.href = '/login';
    }
  };

  return (
    <div className="main-container">
      {/* Header */}
      <header className="main-header">
        <div className="logo">
          <img src="/img/logo.png" alt="웹 페이지 로고" style={{ height: '40px', width: 'auto' }} />
        </div>
        <div className="header-right">
        <button className="profile-icon" onClick={handleProfileClick}>
            {isLoggedIn ? username : '로그인'}
          </button>
        </div>
      </header>
      <div className="search-bar-container">
        <div className="region-selector">
          <label htmlFor="region">지역</label>
          <select id="region" name="region">
            <option value="korea">Korea</option>
            <option value="na">NA</option>
            <option value="eu">EU</option>
          </select>
        </div>
        <div className="search-section">
          <label htmlFor="player-search">검색</label>
          <input type="text" id="player-search" className="search-bar" placeholder="플레이어 이름 + #KR1" />
        </div>
        <div className="branding">
          <span>.RGF</span>
        </div>
      </div>
      <div className="main-contents">
        {/* Banner Section */}
        <div className="main-contents-left">
          <div className="banner">
            <h2>웹 페이지 배너</h2>
          </div>

          {/* Player Cards */}
          <div className="player-cards">
            <div className="player-card">랜덤 플레이어 1</div>
            <div className="player-card">랜덤 플레이어 2</div>
            <div className="player-card">랜덤 플레이어 3</div>
          </div>
        </div>

        <div className="main-contents-right">
          <div className="friend-list">
            <h3>친구 리스트</h3>
            <ul>
              <li>친구 1</li>
              <li>친구 2</li>
              <li>친구 3</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MainPage;
