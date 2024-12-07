// src/components/modals/championModal.js
import React from 'react';
import PropTypes from 'prop-types';
import './championModal.css';

function ChampionModal({ isOpen, onClose, championData }) {
  if (!isOpen) return null;

  const imageUrl = '/img/user.png'; // 기본 이미지 경로로 고정

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
            <h3>{championData.name}</h3>
        </div>

        <p>{championData.description}</p>
        
        <div className="champion-skills-icon">
            <div className="champion-skills-info">P</div>
            <div className="champion-skills-info">Q</div>
            <div className="champion-skills-info">W</div>
            <div className="champion-skills-info">E</div>
            <div className="champion-skills-info">R</div>
        </div>
        <div className="champion-skills-description">

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
  }).isRequired,
};

export default ChampionModal;
