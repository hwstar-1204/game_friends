// record.js
import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import riotApi from '../utils/riotapi';
import Template from './template';
import MatchRecord from './record/MatchRecord';
import './record.css';

function RecordPage() {
  const [mySummonerInfo, setMySummonerInfo] = useState(null);
  const [matchHistory, setMatchHistory] = useState(null);
  const location = useLocation();
  const friendName = new URLSearchParams(location.search).get('friend');
  const friendsData = location.state?.friendsData || [];
  const gameName = localStorage.getItem('gameName');
  const tagLine = localStorage.getItem('tagLine');
  const nickname = localStorage.getItem('nickname');

  useEffect(() => {
    const fetchMyProfile = async () => {
      const encodedGameName = encodeURIComponent(gameName);
      const response = await riotApi.summonerApi(encodedGameName, tagLine);
      console.log(response);
      setMySummonerInfo(response);
    };
    fetchMyProfile();

    const fetchMatchHistory = async () => {
      try {
        const response = await riotApi.matchApi(friendName);
        setMatchHistory(response);
      } catch (error) {
        alert("잠시 후에 다시 시도해주세요.");
        console.error('Error fetching match history:', error);
      }
    };

    fetchMatchHistory();
  }, [friendName]);

  return (
    <Template friendsData={friendsData}>
      <div className="record-page">
        <div className="record-left">
          <div className="record-player-profile">
            <div
              className="friend-profile"
              style={{ 
                backgroundImage: `url(${mySummonerInfo?.profileIconId ? mySummonerInfo?.profileIconId : '/img/user.png'})`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
              }}
            ></div>

            {/* <h3>{mySummonerInfo?.gameName}#{mySummonerInfo?.tagLine}</h3> */}
          </div>
          <div className="record-player-name">
            <h3>{nickname}</h3>
            <h3>{mySummonerInfo?.gameName}#{mySummonerInfo?.tagLine}</h3>  
          </div>

          <div className="rank-games">
            <h3>개인/2인 랭크 게임</h3>
            <ul>
              <li>
                <p>레벨</p>
                <p>티어</p>
                <p>LP</p>
              </li>
              <li>
                <p>Lv.{mySummonerInfo?.summonerLevel}</p>
                <p>{mySummonerInfo?.tier}</p>
                <p>{mySummonerInfo?.leaguePoints} </p>
              </li>
              <li>
                <p>승리</p>
                <p>패배</p>
                <p>승률</p>
              </li>
              <li>
                <p>{mySummonerInfo?.wins}</p>
                <p>{mySummonerInfo?.losses}</p>
                <p>{mySummonerInfo?.leaguePoints} LP</p>
              </li>
              <li>
                <p>최근 수정일</p>
                <p>
                  {mySummonerInfo?.revisionDate ? new Date(mySummonerInfo.revisionDate).toLocaleDateString() : '-'}
                </p>
              </li>
              {/* {mySummonerInfo?.map((game, index) => (
                <li key={index}>
                  <span>{game.season}</span>
                  <span>{game.tier}</span>
                  <span>{game.leaguePoints} LP</span>
                </li>
              ))} */}
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