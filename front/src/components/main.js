// main.js
import React, { useEffect, useState } from 'react';
import Template from './template';
import './main.css';

function MainPage() {
  const [friendsData, setFriendsData] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    // TODO: 데이터베이스에서 친구 데이터를 가져오는 로직 추가
    setFriendsData([
      { name: '친구 1', status: 'online' },
      { name: '친구 2', status: 'in-game' },
      { name: '친구 3', status: 'offline' },
      { name: '친구 4', status: 'in-game' },
      { name: '친구 5', status: 'offline' },
      { name: '친구 6', status: 'offline' },
      { name: '친구 7', status: 'in-game' },
      { name: '친구 8', status: 'offline' },
      { name: '친구 9', status: 'offline' },
      { name: '친구 10', status: 'online' },
      { name: '친구 11', status: 'in-game' },
      { name: '친구 12', status: 'in-game' },
    ]);
  }, []);

  const handleSearch = () => {
    alert(`검색어: ${searchTerm}`);
    // TODO: 검색 기능 추가 (searchTerm을 바탕으로 검색 수행)
  };

  return (
    <Template friendsData={friendsData}>
      <div className="main-contents-left">
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
            <input
              type="text"
              id="player-search"
              className="search-bar"
              placeholder="플레이어 이름 + #태그"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="branding" onClick={handleSearch} style={{ cursor: 'pointer' }}>
            <span>.RGF</span>
          </div>
        </div>
        <div className="banner">
          <h2>웹 페이지 배너</h2>
        </div>
        <div className="player-cards">
          <div className="player-card">랜덤 플레이어 1</div>
          <div className="player-card">랜덤 플레이어 2</div>
          <div className="player-card">랜덤 플레이어 3</div>
        </div>
      </div>
    </Template>
  );
}

export default MainPage;
