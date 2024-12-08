import React, { useState } from 'react';
import riotApi from '../../utils/riotapi';
import './lolConnect.css';

function LolConnectModal({ onClose }) {
  const [gameName, setLolNickname] = useState('');
  const [tagLine, setLolNicknameTag] = useState('');

  const handleLolConnectSubmit = async (e) => {
    e.preventDefault();

    try {
      // URL 인코딩을 사용하여 한글 닉네임 처리
      const encodedGameName = encodeURIComponent(gameName);
      console.log(encodedGameName, tagLine);
      const response = await riotApi.summonerApi(encodedGameName, tagLine);
      console.log(response);
      localStorage.setItem('gameName', response.gameName);
      localStorage.setItem('tagLine', response.tagLine);
      alert('계정 연동이 완료되었습니다.');
      onClose();
    } catch (error) {   
      alert('계정 연동 중 오류가 발생했습니다. 닉네임과 태그를 다시 확인해주세요.');
      console.error(error);
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <h3>계정 연동</h3>
        <form onSubmit={handleLolConnectSubmit}>
          <div className="input-group">
            <label htmlFor="game-name">롤 닉네임: </label>
            <input 
              type="text" 
              id="game-name" 
              name="game-name" 
              value={gameName} 
              onChange={(e) => setLolNickname(e.target.value)} 
              placeholder="롤 닉네임을 입력하세요" 
              required 
            />
          </div>
          <div className="input-group">
            <label htmlFor="tag-line">롤 닉네임 태그: </label>
            <input 
              type="text" 
              id="tag-line" 
              name="tag-line" 
              value={tagLine} 
              onChange={(e) => setLolNicknameTag(e.target.value)} 
              placeholder="롤 닉네임 태그를 입력하세요 (#제외)" 
              required 
            />
          </div>

          <div className="modal-buttons">
            <button type="submit" className="submit-button">연동하기</button>
            <button type="button" className="cancel-button" onClick={onClose}>취소</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default LolConnectModal;
