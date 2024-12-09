// record.js
import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import riotApi from '../utils/riotapi';
import Template from './template';
import MatchRecord from './record/MatchRecord';
import './record.css';

function RecordPage() {
  const [matchHistory, setMatchHistory] = useState(null);
  const location = useLocation();
  const friendName = new URLSearchParams(location.search).get('friend');
  const friendsData = location.state?.friendsData || [];

  useEffect(() => {
    const fetchMatchHistory = async () => {
      try {
        const response = await riotApi.matchApi(friendName);
        console.log('Match history response:', response);
        setMatchHistory(response);
      } catch (error) {
        console.error('Error fetching match history:', error);
      }
    };

    fetchMatchHistory();
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
              {matchHistory?.rankGames?.map((game, index) => (
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
              {matchHistory?.freeGames?.map((game, index) => (
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
            <div className="recent-games-log" style={{ overflowY: 'auto' }}>
              {matchHistory ? (
                Array.isArray(matchHistory) ? (
                  <MatchRecord matches={matchHistory} />
                ) : (
                  <MatchRecord matches={[matchHistory]} />
                )
              ) : (
                <div className="loading">전적을 불러오는 중...</div>
              )}
            </div>
          </div>
        </div>
      </div>
    </Template>
  );
}

export default RecordPage;