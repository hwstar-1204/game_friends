import React, { useState } from 'react';
import Template from './template';
import { useLocation } from 'react-router-dom';
import './champion.css';
import ChampionModal from './modals/championModal';

function Champion() {
  const location = useLocation();
  const friendsData = location.state?.friendsData || [];
  const [selectedChampion, setSelectedChampion] = useState(null);

  const buttons = Array.from({ length: 200 }, (_, index) => {
    const championData = {
      name: `Champion ${index + 1}`, // 챔피언 이름
      imageUrl: `/path/to/image${index + 1}.png`, // 챔피언 이미지
      role: 'Mage, Tank', // 챔피언 역할군 (최대 2개, ',' 표시로 구분)
      description: `Description for Champion ${index + 1}`, // 챔피언 설명
      skills: {
        P: { name: 'Passive', description: 'This is the champion passive skill.' }, // 패시브 설명
        Q: { name: 'Q Skill', description: 'This is the champion Q skill.' }, // Q 스킬 설명
        W: { name: 'W Skill', description: 'This is the champion W skill.' }, // W 스킬 설명
        E: { name: 'E Skill', description: 'This is the champion E skill.' }, // E 스킬 설명
        R: { name: 'R Skill', description: 'This is the champion ultimate skill.' } // R 스킬 설명
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
          backgroundImage: `url(${championData.imageUrl})`, 
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