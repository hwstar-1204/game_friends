// main.js
import React, { useEffect, useState } from 'react';
import Template from './template';
import './main.css';
import friendsApi from '../utils/friendsApi';
import { getRandomUsersByNumber } from '../utils/utilsApi';
import { useNavigate } from 'react-router-dom';

function MainPage() {
  const [friendsData, setFriendsData] = useState([]);
  const [players, setPlayers] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [chatWindow, setChatWindow] = useState(null);
  const navigate = useNavigate();

  const testFriendsData = [  // 친구 데이터 예시 (추후 삭제 예정)
    { name: '친구 1', status: 'online', tier: 'iron', profileImage: '' },
    { name: '친구 2', status: 'in-game', tier: 'bronze', profileImage: '' },
    { name: '친구 3', status: 'offline', tier: 'silver', profileImage: '' },
    { name: '친구 4', status: 'in-game', tier: 'gold', profileImage: '' },
    { name: '친구 5', status: 'offline', tier: 'platinum', profileImage: '' },
    { name: '친구 6', status: 'offline', tier: 'emerald', profileImage: '' },
    { name: '친구 7', status: 'in-game', tier: 'diamond', profileImage: '' },
    { name: '친구 8', status: 'offline', tier: 'master', profileImage: '' },
    { name: '친구 9', status: 'offline', tier: 'grandmaster', profileImage: '' },
    { name: '친구 10', status: 'online', tier: 'challenger', profileImage: '' },
    { name: '친구 11', status: 'in-game', tier: 'platinum', profileImage: '' },
    { name: '친구 12', status: 'in-game', tier: 'platinum', profileImage: '' },
  ];
  const testPlayersData = [
    { nickname: '플레이어 1', tier: 'iron', profileImage: '' },
    { nickname: '플레이어 2', tier: 'bronze', profileImage: '' },
    { nickname: '플레이어 3', tier: 'silver', profileImage: '' },
    { nickname: '플레이어 4', tier: 'gold', profileImage: '' },
    { nickname: '플레이어 5', tier: 'platinum', profileImage: '' },
  ];

  useEffect(() => {
    // 데이터베이스에서 친구 데이터를 가져오는 로직 
    const fetchFriendsList = async () => {
      try {
        const response = await friendsApi.getFriendsList();
        console.log(response);
        setFriendsData(response);
      } catch (error) {
        console.error('Error fetching friends list:', error);
        setFriendsData(testFriendsData);
      }
    };
    fetchFriendsList();

    const fetchRandomUsers = async () => {
      try {
        const response = await getRandomUsersByNumber(5);
        console.log(response);
        setPlayers(response);
      } catch (error) {
        console.error('Error fetching random users:', error);
        setPlayers(testPlayersData);
      }
    };
    fetchRandomUsers();
  }, []);

  const handleSearch = () => {
    // navigate(`/record?friend=${encodeURIComponent(searchTerm)}`);
    navigate(`/record?friend=${searchTerm}`);

    console.log(encodeURIComponent(searchTerm));
  };

  const handleChat = (friendName, friendTier) => {
    if (chatWindow && !chatWindow.closed) {
      chatWindow.location.href = `/chat?friend=${friendName}&tier=${friendTier}`;
      chatWindow.focus(); // 채팅창을 최상위로 가져옴
    } else {
      const newChatWindow = window.open(
        `/chat?friend=${friendName}&tier=${friendTier}`,
        '_blank',
        'width=400,height=600,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes'
      );
      setChatWindow(newChatWindow);
      newChatWindow.focus(); // 새 창을 열 때도 포커스
    }
  };

  const handleAddFriend = (player) => {
    try {
      const response = friendsApi.requestFriend(player.id);
      console.log(response);
    } catch (error) {
      console.error('Error adding friend:', error);
    }

  };

  const handleTest = async () => {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      navigate('/matching');
    } else {
      alert('로그인 후 이용해주세요.');
    }
  };

  return (
    <Template friendsData={friendsData}>
      <div className="main-contents-left">
        <div className="search-bar-container">
          <div className="region-selector">
            <label htmlFor="region">계정</label>
            <select id="region" name="region">
              <option value="korea">아이디</option>
              <option value="na">태그</option>
            </select>
          </div>
          <div className="search-section">
            <label htmlFor="player-search">검색</label>
            <input
              type="text"
              id="player-search"
              className="search-bar"
              placeholder="플레이어를 입력하세요."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="branding" onClick={handleSearch} style={{ cursor: 'pointer' }}>
            <span>.RGF</span>
          </div>
        </div>
        <div
          className="banner"
          onClick={handleTest}
          style={{
            backgroundImage: "url('/img/banner.png')",
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
        ><div>랜덤 매칭을 시작하려면 클릭하세요.</div></div>


        
        
        <div className="player-cards-banner">
          <h2>플레이어 추천</h2>
        </div>
        <div className="player-cards-container">
          {players.map((player, index) => (
            <div key={index} className="player-card">
              <div
                className="player-profile"
                style={{ 
                  backgroundImage: `url(${player.profileImage ? player.profileImage : '/img/user.png'})`,
                  backgroundSize: 'cover',
                  backgroundPosition: 'center',
                }}
              ></div>
              <div className="player-info-row">
                <p className="player-name">{player.nickname}</p>
                <img src={`/img/tiers/${player.tier}.png`} alt="티어 이미지" className="player-tier" />
              </div>
              <div className="player-summonerinfo-row">
                <p className="player-gameName">{player.gameName}</p>
                <p className="player-tagLine">#{player.tagLine}</p>
              </div>
              <div className="player-actions">
                <button className="player-button" onClick={() => navigate(`/record?friend=${player.nickname}`)}>전적 보기</button>
                <button className="player-button" onClick={() => handleChat(player.name, player.tier)}>채팅</button>
                
                <button 
                  className="player-button" 
                  onClick={() => handleAddFriend(player)}
                >
                  +
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </Template>
  );
}

export default MainPage;
