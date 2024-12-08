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
      const championName = championInfo.name;
      const role = championInfo.tags[0] + ', ' + (championInfo.tags[1] ? championInfo.tags[1] : '');
      const description = championInfo.blurb;
      const spells = championInfo.spells;

      const championData = {
        name: championName,
        imageUrl: champion.imageUrl,
        role: role,
        description: description,
        skills: {
          P: { name: 'Passive', description: 'This is the champion passive skill.', imageUrl: `/path/to/skill_P.png` },
          Q: { name: spells[0].name, description: spells[0].description, imageUrl: spells[0].image.full },
          W: { name: spells[1].name, description: spells[1].description, imageUrl: spells[1].image.full },
          E: { name: spells[2].name, description: spells[2].description, imageUrl: spells[2].image.full },
          R: { name: spells[3].name, description: spells[3].description, imageUrl: spells[3].image.full }
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