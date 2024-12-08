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


  const buttons = champions.map((champion, index) => {
    const championData = {
      name: champion.name,
      imageUrl: champion.imageUrl,
      role: 'Mage, Tank', // 챔피언 역할군 (최대 2개, ',' 표시로 구분)
      description: `Description for ${champion.name}`, // 챔피언 설명
      skills: {
        P: { name: 'Passive', description: 'This is the champion passive skill.', imageUrl: `/path/to/skill${index + 1}_P.png` },
        Q: { name: 'Q Skill', description: 'This is the champion Q skill.', imageUrl: `/path/to/skill${index + 1}_Q.png` },
        W: { name: 'W Skill', description: 'This is the champion W skill.', imageUrl: `/path/to/skill${index + 1}_W.png` },
        E: { name: 'E Skill', description: 'This is the champion E skill.', imageUrl: `/path/to/skill${index + 1}_E.png` },
        R: { name: 'R Skill', description: 'This is the champion ultimate skill.', imageUrl: `/path/to/skill${index + 1}_R.png` }
      },
    };

    return (
      <button 
        key={index} 
        className="image-button"
        onClick={() => setSelectedChampion(championData)}
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
          {buttons}
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