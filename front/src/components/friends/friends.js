// friends.js
import React, { useEffect, useState } from 'react';
import './friends.css';

function FriendList({ friendsData, onChat, onRecord }) {
  const [friends, setFriends] = useState([]);
  const [tooltip, setTooltip] = useState({ visible: false, text: '', x: 0, y: 0 });

  useEffect(() => {
    if (friendsData) {
      const sortedFriends = [...friendsData].sort((a, b) => {
        const statusOrder = {
          'online': 1,
          'in-game': 2,
          'offline': 3
        };
        return statusOrder[a.status] - statusOrder[b.status];
      });
      setFriends(sortedFriends);
    }
  }, [friendsData]);

  const handleMouseEnter = (event, status) => {
    const { clientX, clientY } = event;
    setTooltip({
      visible: true,
      text: status === 'online' ? '온라인' : status === 'in-game' ? '게임 중' : '오프라인',
      x: clientX,
      y: clientY - 30,
    });
  };

  const handleMouseLeave = () => {
    setTooltip({ visible: false, text: '', x: 0, y: 0 });
  };

  const handleRemoveFriend = (friendName) => {
    const confirmDelete = window.confirm(`${friendName} 님을 친구 목록에서 삭제하시겠습니까?`);
    if (confirmDelete) {
      const updatedFriends = friends.filter((friend) => friend.name !== friendName);
      setFriends(updatedFriends);
      alert(`${friendName} 님이 친구 목록에서 삭제되었습니다.`);
    }
  };

  return (
    <div className="friend-list">
      <h3>친구 리스트</h3>
      <ul>
        {friends.length > 0 ? (
          friends.map((friend, index) => (
            <li key={index} className={friend.status}>
              <div
                className="friend-profile"
                style={{ 
                  backgroundImage: `url(${friend.profileImage ? friend.profileImage : '/img/user.png'})`,
                  backgroundSize: 'cover',
                  backgroundPosition: 'center',
                }}
              ></div>

              <div className="friend-info">
                <div className="friend-info-row">
                  <img src={`/img/tiers/${friend.tier}.png`} alt="티어 이미지" className="friend-tier" />
                  <p className="friend-name">{friend.name}</p>
                </div>
                <div className="friend-actions">
                  <button className="friend-button" onClick={() => onRecord(friend.name)}>전적 보기</button>
                  <button
                    className="friend-button"
                    onClick={() => onChat(friend.name, friend.tier)}
                  >
                    채팅
                  </button>
                  <button 
                    className="friend-button" 
                    onClick={() => handleRemoveFriend(friend.name)}
                  >
                    -
                  </button>
                </div>
              </div>
              <div
                className={`friend-status ${friend.status}`}
                onMouseEnter={(e) => handleMouseEnter(e, friend.status)}
                onMouseLeave={handleMouseLeave}
              ></div>
            </li>
          ))
        ) : (
          <li>친구가 없습니다.</li>
        )}
      </ul>
      {tooltip.visible && (
        <div className="tooltip" style={{ top: tooltip.y, left: tooltip.x,}} >
          {tooltip.text}
        </div>
      )}
    </div>
  );
}

export default FriendList;
