import React from 'react';
import './MatchRecord.css';

function MatchRecord({ matches }) {
  if (!matches || !Array.isArray(matches) || matches.length === 0) {
    return <div className="no-matches">최근 게임 기록이 없습니다.</div>;
  }

  const calculateGameDuration = (duration) => {
    const minutes = Math.floor(duration / 60);
    const seconds = duration % 60;
    return `${minutes}분 ${seconds}초`;
  };

  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className="match-history">
      {matches.map((match, index) => {
        if (!match || !match.matchInfoDTO || !match.playerStatsDTO) {
          console.error('Invalid match data:', match);
          return null;
        }

        const { matchInfoDTO, playerStatsDTO } = match;
        
        const currentPlayer = playerStatsDTO.find(player => 
          player.riotIdGameName === new URLSearchParams(window.location.search).get('friend')
        );
        
        const isWin = currentPlayer?.win || false;

        return (
          <div 
            key={matchInfoDTO.gameId} 
            className={`match-record ${isWin ? 'win' : 'defeat'}`}
          >
            <div className="match-info">
              <div className="game-type">
                <span>{matchInfoDTO.gameMode}</span>
                <span>{calculateGameDuration(matchInfoDTO.gameDuration)}</span>
                <span>{formatDate(matchInfoDTO.gameEndTimestamp)}</span>
                <span className="result">{isWin ? '승리' : '패배'}</span>
              </div>
            </div>

            <div className="teams-container">
              <div className="team blue-team">
                {playerStatsDTO.slice(0, 5).map((player) => (
                  <div key={player.puuid} className="player-row">
                    <div className="champion-info">
                      <img 
                        src={player.championImageUrl} 
                        alt={player.championName}
                        className="champion-icon" 
                      />
                      <div className="summoner-spells">
                        <img src={player.summonerSpell1Url} alt="spell1" />
                        <img src={player.summonerSpell2Url} alt="spell2" />
                      </div>
                    </div>
                    
                    <div className="player-info">
                      <span className="player-name">{player.riotIdGameName}</span>
                      <div className="kda">
                        <span>{player.kills}</span>/
                        <span className="deaths">{player.deaths}</span>/
                        <span>{player.assists}</span>
                        <span className="kda-ratio">
                          {((player.kills + player.assists) / Math.max(1, player.deaths)).toFixed(2)} 평점
                        </span>
                      </div>
                    </div>

                    <div className="items">
                      {[...Array(7)].map((_, i) => (
                        <div key={i} className="item-slot">
                          {player[`item${i}`] !== "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/item/0.png" && (
                            <img src={player[`item${i}`]} alt={`item${i}`} />
                          )}
                        </div>
                      ))}
                    </div>

                    <div className="stats">
                      <span>CS {player.totalMinionsKilled}</span>
                      <span>시야 점수 {player.visionScore}</span>
                    </div>
                  </div>
                ))}
              </div>

              <div className="team red-team">
                {playerStatsDTO.slice(5, 10).map((player) => (
                  <div key={player.puuid} className="player-row">
                    <div className="champion-info">
                      <img 
                        src={player.championImageUrl} 
                        alt={player.championName}
                        className="champion-icon" 
                      />
                      <div className="summoner-spells">
                        <img src={player.summonerSpell1Url} alt="spell1" />
                        <img src={player.summonerSpell2Url} alt="spell2" />
                      </div>
                    </div>
                    
                    <div className="player-info">
                      <span className="player-name">{player.riotIdGameName}</span>
                      <div className="kda">
                        <span>{player.kills}</span>/
                        <span className="deaths">{player.deaths}</span>/
                        <span>{player.assists}</span>
                        <span className="kda-ratio">
                          {((player.kills + player.assists) / Math.max(1, player.deaths)).toFixed(2)} 평점
                        </span>
                      </div>
                    </div>

                    <div className="items">
                      {[...Array(7)].map((_, i) => (
                        <div key={i} className="item-slot">
                          {player[`item${i}`] !== "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/item/0.png" && (
                            <img src={player[`item${i}`]} alt={`item${i}`} />
                          )}
                        </div>
                      ))}
                    </div>

                    <div className="stats">
                      <span>CS {player.totalMinionsKilled}</span>
                      <span>시야 점수 {player.visionScore}</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default MatchRecord; 