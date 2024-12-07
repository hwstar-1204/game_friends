// src/components/champion.js
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
      name: `Champion ${index + 1}`,
      imageUrl: `/path/to/image${index + 1}.png`,
      role: 'Role Info',
      description: `Description for Champion ${index + 1}`
    };

    return (
      <button 
        key={index} 
        className="image-button"
        onClick={() => setSelectedChampion(championData)}
        style={{ 
          width: '100px', 
          height: '100px', 
          margin: '5px', 
          backgroundImage: `url(${championData.imageUrl})`, 
          backgroundSize: 'cover', 
          backgroundPosition: 'center' 
        }}
      >
      </button>
    );
  });

  return (
    <Template friendsData={friendsData}>
      <div className="champion-page">
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
