import React, { useState, useEffect } from 'react';
import friendsApi from '../../utils/friendsApi';
import './friendRequests.css';

function FriendRequestsModal({ onClose }) {
  const [friendRequests, setFriendRequests] = useState([]);

  useEffect(() => {
    const fetchFriendRequests = async () => {
      try {
        const response = await friendsApi.getFriendRequests();
        if (response.length > 0) {
          setFriendRequests(response);
        }
      } catch (error) {
        console.error('친구 요청 목록을 불러오는데 실패했습니다:', error);
        alert('친구 요청 목록을 불러오는데 실패했습니다. 다시 시도해주세요.');
      }
    };

    fetchFriendRequests();
  }, []);

  const handleAccept = async (friendId) => {
    try {
        const response = await friendsApi.acceptFriend(friendId);
        if (response.status === 200 || response.ok) {
            setFriendRequests(prevRequests => 
                prevRequests.filter(request => request.id !== friendId)
            );
            alert('친구 요청이 성공적으로 수락되었습니다.');
        }
    } catch (error) {
        console.error('친구 요청 수락 중 오류:', error);
        alert('친구 요청 수락 중 오류가 발생했습니다. 다시 시도해주세요.');
    }
  };

  const handleReject = async (friendId) => {
    try {
      const response = await friendsApi.rejectFriend(friendId);
      
      if (response.status === 200 || response.ok) {
        setFriendRequests(prevRequests => 
          prevRequests.filter(request => request.id !== friendId)
        );
        alert('친구 요청이 거절되었습니다.');
      }
    } catch (error) {
      console.error('친구 요청 거절에 실패했습니다:', error.message);
      alert('친구 요청 거절 중 오류가 발생했습니다. 다시 시도해주세요.');
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <h3>친구 요청 목록</h3>
        <div className="friend-requests-list">
          {friendRequests.length === 0 ? (
            <p className="no-requests">대기 중인 친구 요청이 없습니다.</p>
          ) : (
            friendRequests.map((request) => (
              <div key={request.id} className="friend-request-item">
                <span className="friend-nickname">{request.nickname}</span>
                <div className="request-buttons">
                  <button 
                    className="accept-button"
                    onClick={() => handleAccept(request.id)}
                  >
                    수락
                  </button>
                  <button 
                    className="reject-button"
                    onClick={() => handleReject(request.id)}
                  >
                    거절
                  </button>
                </div>
              </div>
            ))
          )}
        </div>
        <div className="modal-buttons">
          <button onClick={onClose}>닫기</button>
        </div>
      </div>
    </div>
  );
}

export default FriendRequestsModal;
