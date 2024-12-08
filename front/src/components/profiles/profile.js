import React, { useEffect, useState } from 'react';
import riotApi from '../../utils/riotapi';
import './profile.css';


function ProfilePage() {
    const [mySummonerInfo, setMySummonerInfo] = useState(null);
    // 게임 닉네임, 태그라인 데이터를 
    const gameName = localStorage.getItem('gameName');
    const tagLine = localStorage.getItem('tagLine');
    
    useEffect(() => {
        const fetchMyProfile = async () => {
          console.log(gameName, tagLine);
          const encodedGameName = encodeURIComponent(gameName);
          const response = await riotApi.summonerApi(encodedGameName, tagLine);
          console.log(response);
            setMySummonerInfo(response);
        };
        fetchMyProfile();
    }, []);

    
    // API로 받는 프로필 정보
    //     "id": "HGdBTVF7W2JB5Yo5j7L4Nk2FCQJpqbDX2MpDvcB5BVvwAzE",
    //     "profileIconId": 5804,
    //     "revisionDate": 1715452151000,
    //     "summonerId": null,
    //     "puuId": "xhL78yHAz2R7i5aRKj2JPiJmUSdbx_OAGpZbJ12XK3rZAAIkZToUqcVKZRpfsNle6q73ByU2OKRKAw",
    //     "summonerLevel": 254,
    //     "leagueId": null,
    //     "queueType": null,
    //     "tier": null,
    //     "rank": null,
    //     "leaguePoints": 0,
    //     "wins": 0,
    //     "losses": 0


  return ( // 틀만 만들어놓은 상태 (디자인 수정 필요)
      <div className="profile-container">
        <div className="profile-header">

          <div className="profile">
            <img 
              // src={`/img/profileicon/${mySummonerInfo?.profileIconId}.png`}  // 롤 프로필 아이콘 이미지 
              src={`/img/user.png`}
              alt="프로필 아이콘" 
              className="profile-icon-img"
            />
            <h3 className="summoner-level">Lv.{mySummonerInfo?.summonerLevel}</h3>
          </div>

          <div className="profile-info">
            {/* <h1 className="summoner-name">{mySummonerInfo?.id}</h1> // 롤 서버 내 id*/}
            <h1 className="summoner-name">{gameName}#{tagLine}</h1>
            <div className="rank-info">
              <img 
                src={`/img/tiers/${mySummonerInfo?.tier?.toLowerCase()}.png`}
                alt="티어 아이콘"
                className="tier-icon"
              />
              <span className="tier-text">
                {mySummonerInfo?.tier} {mySummonerInfo?.rank} ({mySummonerInfo?.leaguePoints}LP)
              </span>
            </div>
          </div>
        </div>

        <div className="profile-stats">
          <div className="stats-box">
            <h3>전적</h3>
            <div className="win-loss">
              <div className="wins">
                <span className="stat-label">승리</span>
                <span className="stat-value">{mySummonerInfo?.wins}</span>
              </div>
              <div className="losses">
                <span className="stat-label">패배</span>
                <span className="stat-value">{mySummonerInfo?.losses}</span>
              </div>
            </div>
          </div>

          <div className="stats-box">
            <h3>상세 정보</h3>
            <div className="details">
              <div className="detail-item">
                <span className="detail-label">큐 타입:</span>
                <span className="detail-value">{mySummonerInfo?.queueType || '-'}</span>
              </div>
              <div className="detail-item">
                <span className="detail-label">리그 ID:</span>
                <span className="detail-value">{mySummonerInfo?.leagueId || '-'}</span>
              </div>
              <div className="detail-item">
                <span className="detail-label">최근 수정일:</span>
                <span className="detail-value">
                  {mySummonerInfo?.revisionDate ? new Date(mySummonerInfo.revisionDate).toLocaleDateString() : '-'}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
  )
}

export default ProfilePage;