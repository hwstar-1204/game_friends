import React, { useState, useEffect } from 'react';
import friendsApi from '../../utils/friendsApi';
import './friendRequests.css';

function FriendRequestsModal({ onClose }) {
  const [friendRequests, setFriendRequests] = useState([]);

  useEffect(() => {
    const fetchFriendRequests = async () => {
      try {
        const response = await friendsApi.getFriendRequests();
        // API 응답에서 대기 중인 친구 요청만 필터링
        // setFriendRequests(response.data.filter(request => request.status === 'pending'));
        console.log(response.data, "response.data")
        if (response.data.length > 0) {
            console.log(response.data)
          setFriendRequests(response.data);
        }
      } catch (error) {
        console.error('친구 요청 목록을 불러오는데 실패했습니다:', error);
      }
    };

    fetchFriendRequests();
  }, []);

  const handleAccept = async (friendId) => {
    try {
      await friendsApi.acceptFriend(friendId);
      // 수락된 요청을 목록에서 제거
      setFriendRequests(prevRequests => 
        prevRequests.filter(request => request.id !== friendId)
      );
    } catch (error) {
      console.error('친구 요청 수락에 실패했습니다:', error);
    }
  };

  const handleReject = async (friendId) => {
    try {
      await friendsApi.rejectFriend(friendId);
      // 거절된 요청을 목록에서 제거
      setFriendRequests(prevRequests => 
        prevRequests.filter(request => request.id !== friendId)
      );
    } catch (error) {
      console.error('친구 요청 거절에 실패했습니다:', error);
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
