import React, { useState, useEffect } from 'react';
import Template from './template';
import { useLocation } from 'react-router-dom';
import './champion.css';
import ChampionModal from './modals/championModal';
import riotApi from '../utils/riotapi';

function Champion() {
  const location = useLocation();
  const friendsData = location.state?.friendsData || [];
  const [selectedChampion, setSelectedChampion] = useState(null);
  const [champions, setChampions] = useState([]);

  useEffect(() => {
    const fetchChampionList = async () => {
      const response = await riotApi.getChampionList();
      setChampions(response);
    };
    fetchChampionList();
  }, []);

  const handleChampionClick = async (champion) => {
    try {
      const championInfo = await riotApi.getChampionInfo(champion.name);
      // console.log('챔피언 상세 정보:', championInfo);
      const role = championInfo.tags[0] + ', ' + (championInfo.tags[1] ? championInfo.tags[1] : '');
      const championData = {
        name: champion.name,
        imageUrl: champion.imageUrl,
        role: role,
        description: `Description for ${champion.name}`,
        skills: {
          P: { name: 'Passive', description: 'This is the champion passive skill.', imageUrl: `/path/to/skill_P.png` },
          Q: { name: 'Q Skill', description: 'This is the champion Q skill.', imageUrl: `/path/to/skill_Q.png` },
          W: { name: 'W Skill', description: 'This is the champion W skill.', imageUrl: `/path/to/skill_W.png` },
          E: { name: 'E Skill', description: 'This is the champion E skill.', imageUrl: `/path/to/skill_E.png` },
          R: { name: 'R Skill', description: 'This is the champion ultimate skill.', imageUrl: `/path/to/skill_R.png` }
        },
      };
      setSelectedChampion(championData);
    } catch (error) {
      console.error('챔피언 정보 조회 중 오류 발생:', error);
    }
  };

  const detailButtons = champions.map((champion, index) => {
    return (
      <button 
        key={index} 
        className="image-button"
        onClick={() => handleChampionClick(champion)}
        style={{ 
          width: '100px', 
          height: '100px', 
          backgroundImage: `url(${champion.imageUrl})`, 
          backgroundSize: 'cover', 
          backgroundPosition: 'center',
          backgroundColor: '#333',
        }}
      >
      </button>
    );
  });

  return (
    <Template friendsData={friendsData}>
      <div className="champion-page">
        <div className="champion-header">
          <h1 className="champion-title">챔피언 목록</h1>
        </div>
        <div className="image-button-grid">
          {detailButtons}
        </div>
      </div>
      {selectedChampion && (
        <ChampionModal 
          isOpen={!!selectedChampion} 
          onClose={() => setSelectedChampion(null)} 
          championData={selectedChampion} 
        />
      )}
    </Template>
  );
}

export default Champion;