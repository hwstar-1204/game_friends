import React, { useEffect, useState } from 'react';
import './friends.css';

function FriendList({ friendsData, onChat }) {
  const [friends, setFriends] = useState([]);
  const [tooltip, setTooltip] = useState({ visible: false, text: '', x: 0, y: 0 });

  useEffect(() => {
    // 데이터베이스에서 전달된 친구 데이터를 설정합니다.
    if (friendsData) {
      setFriends(friendsData);
    }
  }, [friendsData]);

  const handleMouseEnter = (event, status) => {
    const { clientX, clientY } = event;
    setTooltip({
      visible: true,
      text: status === 'online' ? '온라인' : status === 'in-game' ? '게임 중' : '오프라인',
      x: clientX,
      y: clientY - 30, // 커서 위에 툴팁이 표시되도록 y 좌표를 조정합니다.
    });
  };

  const handleMouseLeave = () => {
    setTooltip({ visible: false, text: '', x: 0, y: 0 });
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
                style={{ backgroundImage: `url(${friend.profileImage})` }}
              ></div>
              <div className="friend-info">
                <div className="friend-info-2">
                  <p className="friend-name">{friend.name}</p>
                  <p className="friend-tier">{friend.tier}</p>
                </div>
                <div className="friend-actions">
                  <button className="friend-button">전적 보기</button>
                  <button
                    className="friend-button"
                    onClick={() => onChat(friend.name)}
                  >
                    채팅
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
