// record.js
import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Template from './template';
import './record.css';

function RecordPage() {
  const [recordData, setRecordData] = useState(null);
  const location = useLocation();
  const friendName = new URLSearchParams(location.search).get('friend');
  const friendsData = location.state?.friendsData || [];

  useEffect(() => {
    setRecordData({
      friendName,
      
      // 솔로랭크 티어 및 lp 포인트
      rankGames: [
        { season: 'S2024 S2', tier: 'Emerald 4', lp: 43 },
        { season: 'S2024 S1', tier: 'Platinum 2', lp: 80 },
        { season: 'S2023 S1', tier: 'Gold 3', lp: 85 },
        { season: 'S2022', tier: 'Platinum 4', lp: 0 },
      ],
      // 자유랭크 티어 및 lp 포인트
      freeGames: [
        { season: 'S2024 S2', tier: 'Silver 2', lp: 62 },
        { season: 'S2024 S1', tier: 'Platinum 4', lp: 52 },
        { season: 'S2023 S1', tier: 'Silver 3', lp: 33 },
      ],
      // 최근 전적
      recentGames: [
        {
          type: '무작위 총력전', // 게임 타입
          outcome: '승리', // 승리 or 패배 or 무승부
          matchTimeAgo: '22분 전', // 시간
          kda: '5 / 5 / 28', // KDA
          champImg: '/img/user.png', // 챔피언 이미지
          champ: '챔피언 1', // 챔피언 이름
          lane: '4th', // 기여도
          cs: '17', // cs 개수
          avgCs: '3.4', // 분당 cs
          participantsRed: ['플레이어 1', '플레이어 2', '플레이어 3', '플레이어 4'], // 레드팀
          participantsBlue: ['플레이어 5', '플레이어 6', '플레이어 7', '플레이어 8'], // 블루팀
        },
        {
          type: '무작위 총력전',
          outcome: '패배',
          matchTimeAgo: '22분 전',
          kda: '5 / 5 / 28',
          champImg: '/img/user.png',
          champ: '챔피언 1',
          lane: '4th',
          cs: '17',
          avgCs: '3.4',
          participantsRed: ['플레이어 1', '플레이어 2', '플레이어 3', '플레이어 4'],
          participantsBlue: ['플레이어 5', '플레이어 6', '플레이어 7', '플레이어 8'],
        },
      ],
    });
  }, [friendName]);

  return (
    <Template friendsData={friendsData}>
        <div className="record-page">
          <div className="record-left">
            <div className="record-player-info">
              <div
                className="record-player-profile"
                style={{ 
                  backgroundImage: `url(${ '/img/user.png'})`,
                  backgroundSize: 'cover',
                  backgroundPosition: 'center',
                }}
              ></div>
              
              <h3>{friendName}</h3>

            </div>

            <div className="rank-games">
              <h3>개인/2인 랭크 게임</h3>
              <ul>
                  {recordData?.rankGames?.map((game, index) => (
                  <li key={index}>
                      <span>{game.season}</span>
                      <span>{game.tier}</span>
                      <span>{game.lp} LP</span>
                  </li>
                  ))}
                </ul>
            </div>

            <div className="free-games">
              <h3>자유 랭크 게임</h3>
              <ul>
                {recordData?.freeGames?.map((game, index) => (
                  <li key={index}>
                    <span>{game.season}</span>
                    <span>{game.tier}</span>
                    <span>{game.lp} LP</span>
                  </li>
                ))}
              </ul>
            </div>
          </div>

          <div className="record-right">

          
            <div className="recent-games">
              <h3>최근 게임</h3>
                <div className="recent-games-log" style={{ overflowY: 'auto', maxHeight: '700px' }}>
                  {recordData?.recentGames?.map((game, index) => (
                    <div key={index} className={`game ${game.outcome === '승리' ? 'win' : 'lose'}`}>
                      <div className="recent-games-log-left"> 
                        <p>{game.type}</p>
                        <p>{game.outcome} ({game.matchTimeAgo})</p>

                      </div>

                      <div className="recent-games-log-center"> 
                        <div
                          className="recent-champ-img"
                          style={{ 
                            backgroundImage: `url(${ '/img/user.png'})`,
                            backgroundSize: 'cover',
                            backgroundPosition: 'center',
                          }}
                        ></div>
                        <p>{game.champ} - {game.kda}</p>
                        <p>CS: {game.cs} ({game.avgCs})</p>
                      </div>
                      
                      <div className="recent-games-log-right"> 
                        <div className="recent-games-log-right-red"> 
                          <p>{game.participantsRed.join(', ')}</p>
                        </div> 

                        <div className="recent-games-log-right-blue"> 
                          <p>{game.participantsBlue.join(', ')}</p>
                        </div> 

                      </div>
                    </div>
                  ))}

                </div>
            </div>
          </div>
        </div>
    </Template>
  );
}

export default RecordPage;