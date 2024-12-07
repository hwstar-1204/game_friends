import React, { useState } from 'react';
import PropTypes from 'prop-types';
import './championModal.css';

function ChampionModal({ isOpen, onClose, championData }) {
  const [skillDescription, setSkillDescription] = useState({
    name: '스킬 설명',
    description: '확인하려는 스킬을 클릭하세요.',
    imageUrl: ''
  });

  if (!isOpen) return null;

  const handleSkillClick = (skill) => {
    const skillData = championData.skills[skill];
    if (skillData) {
      setSkillDescription(skillData);
    }
  };
  

  const imageUrl = championData.imageUrl;

  return (
    <div className="champion-modal-overlay" onClick={onClose}>
      <div className="champion-modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="champion-close-button" onClick={onClose}>X</button>
        <div className="champion-info">
            <div
              className="champion-image"
              style={{
                  backgroundImage: `url(${imageUrl})`,
              }}
            ></div>
            <div className="champion-name">
              <h4>{championData.role.split(', ').slice(0, 2).join(' / ')}</h4>
              <h3>{championData.name}</h3>
            </div>
        </div>

        <p>{championData.description}</p>
        
        <div className="champion-skills-icon">
            <div className="champion-skills-info" onClick={() => handleSkillClick('P')}>P</div>
            <div className="champion-skills-info" onClick={() => handleSkillClick('Q')}>Q</div>
            <div className="champion-skills-info" onClick={() => handleSkillClick('W')}>W</div>
            <div className="champion-skills-info" onClick={() => handleSkillClick('E')}>E</div>
            <div className="champion-skills-info" onClick={() => handleSkillClick('R')}>R</div>
        </div>
        
        <div className="champion-skills-description">
          <div className="champion-skills-name">
            <div
                className="skills-image"
                style={{
                    backgroundImage: `url(${skillDescription.imageUrl})`,
                    display: skillDescription.imageUrl ? 'flex' : 'none',
                }}
              ></div>

            <h2>{skillDescription.name}</h2>
          </div>
          <h4>{skillDescription.description}</h4>
        </div>
      </div>
    </div>
  );
}

ChampionModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
  championData: PropTypes.shape({
    name: PropTypes.string.isRequired,
    imageUrl: PropTypes.string,
    role: PropTypes.string.isRequired,
    description: PropTypes.string,
    skills: PropTypes.shape({
      P: PropTypes.shape({
        name: PropTypes.string,
        description: PropTypes.string,
        imageUrl: PropTypes.string,
      }),
      Q: PropTypes.shape({
        name: PropTypes.string,
        description: PropTypes.string,
        imageUrl: PropTypes.string,
      }),
      W: PropTypes.shape({
        name: PropTypes.string,
        description: PropTypes.string,
        imageUrl: PropTypes.string,
      }),
      E: PropTypes.shape({
        name: PropTypes.string,
        description: PropTypes.string,
        imageUrl: PropTypes.string,
      }),
      R: PropTypes.shape({
        name: PropTypes.string,
        description: PropTypes.string,
        imageUrl: PropTypes.string,
      }),
    }).isRequired,
  }).isRequired,
};


export default ChampionModal;