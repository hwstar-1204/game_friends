import React, { useEffect, useState, useRef } from 'react';
import websocketService from '../../utils/websocket';
import './MatchingComponent.css';

const MatchingComponent = () => {
    const [matchStatus, setMatchStatus] = useState('대기 중');
    const [matchedUser, setMatchedUser] = useState(null);
    const [connectionStatus, setConnectionStatus] = useState('연결 중...');
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const chatBoxRef = useRef(null);
    const [currentUserId, setCurrentUserId] = useState(null);

    useEffect(() => {
        console.log('컴포넌트 마운트 - 웹소켓 연결 시도');
        
        // 현재 사용자 ID 가져오기
        const getCurrentUserId = async () => {
            const userId = await websocketService.getMemberIdFromToken();
            console.log("현재 사용자 ID 설정:", userId);
            setCurrentUserId(userId);
        };
        getCurrentUserId();
    }, []);

    useEffect(() => {
        if (!currentUserId) return;

        console.log('웹소켓 연결 및 구독 시작, currentUserId:', currentUserId);
        
        try {
            websocketService.connect();
            
            // 매칭 이벤트 구독
            const matchUnsubscribe = websocketService.subscribe('match', (matchData) => {
                console.log('매칭 데이터 수신:', matchData);
                
                if (!matchData) {
                    console.error('매칭 데이터가 없습니다');
                    return;
                }

                // 매칭 데이터 구조 상세 로깅
                console.log('매칭 데이터 상세:', {
                    receivedData: matchData,
                    type: typeof matchData,
                    keys: Object.keys(matchData)
                });

                setMatchStatus('매칭 성공!');
                
                // matchData 구조 확인
                console.log('매칭된 유저 데이터:', {
                    memberId: matchData.memberId,
                    gameName: matchData.gameName,
                    tagLine: matchData.tagLine,
                    tier: matchData.tier,
                    rank: matchData.rank,
                    chatRoomId: matchData.chatRoomId
                });

                if (!matchData.memberId) {
                    console.error('매칭된 유저의 ID가 없습니다');
                    return;
                }

                // 매칭 유저 정보 설정
                setMatchedUser({
                    id: matchData.memberId,
                    gameName: matchData.gameName,
                    tagLine: matchData.tagLine,
                    tier: matchData.tier,
                    rank: matchData.rank
                });

                // 매칭 성공 시 상태 확인
                console.log('매칭 후 상태:', {
                    matchStatus: '매칭 성공!',
                    matchedUser: matchData,
                    chatRoomId: matchData.chatRoomId
                });
            });

            // 채팅 메시지 구독
            const chatUnsubscribe = websocketService.subscribe('chat', (chatData) => {
                console.log('채팅 메시지 수신:', chatData);
                
                // 현재 사용자의 ID와 비교하여 메시지 방향 결정
                const isSentByMe = String(chatData.sender) === String(currentUserId);
                console.log('메시지 방향 확인:', {
                    sender: chatData.sender,
                    currentUserId,
                    isSentByMe
                });

                // 새 메시지 추가
                setMessages(prev => [...prev, {
                    sender: chatData.sender,
                    content: chatData.message,
                    timestamp: new Date(),
                    isSentByMe  // isSent 대신 isSentByMe 사용
                }]);

                // 스크롤을 최신 메시지로 이동
                if (chatBoxRef.current) {
                    chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
                }
            });

            // 연결 상태 확인을 위한 인터벌
            const connectionCheck = setInterval(() => {
                const isConnected = websocketService.stompClient?.connected;
                setConnectionStatus(isConnected ? '연결됨' : '연결 안됨');
            }, 2000);

            // 컴포넌트 언마운트 시 정리
            return () => {
                console.log('구독 해제 및 연결 정리');
                clearInterval(connectionCheck);
                matchUnsubscribe();
                chatUnsubscribe();
            };
        } catch (error) {
            console.error('웹소켓 연결 시도 중 에러:', error);
            setConnectionStatus('연결 실패');
        }
    }, [currentUserId]);

    // 컴포넌트가 완전히 제거될 때 연결 종료
    useEffect(() => {
        return () => {
            console.log('컴포넌트 완전 제거 - 연결 종료');
            websocketService.disconnect();
        };
    }, []);

    // 새 메시지가 추가될 때마다 스크롤을 아래��� 이동
    useEffect(() => {
        if (chatBoxRef.current) {
            chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
        }
    }, [messages]);

    const handleMatchRequest = async () => {
        console.log('매칭 요청 시작');
        setMatchStatus('매칭 중...');
        
        try {
            await websocketService.sendMatchRequest();
            console.log('매칭 요청 메시지 전송 완료');
        } catch (error) {
            console.error('매칭 요청 처리 중 에러:', error);
            setMatchStatus('매칭 요청 실패');
        }
    };

    const handleSendMessage = async () => {
        if (!newMessage.trim()) return;

        try {
            // 채팅 메시지 전송
            await websocketService.sendChatMessage(newMessage.trim());
            setNewMessage(''); // 입력창 비우기
        } catch (error) {
            console.error('메시지 전송 중 에러:', error);
        }
    };

    return (
        <div className="matching-container">
            <h2>매칭 시스템</h2>
            <div className="connection-status">
                웹소켓 상태: {connectionStatus}
            </div>
            <div className="status-display">
                현재 상태: {matchStatus}
            </div>

            <div className="matching-section">
                <button 
                    onClick={handleMatchRequest}
                    disabled={matchStatus === '매칭 중...' || connectionStatus !== '연결됨'}
                    className="match-button"
                >
                    매칭 시작
                </button>
            </div>

            {matchedUser && (
                <div className="chat-section">
                    <div className="matched-user-info">
                        <h3>매칭된 유저 정보</h3>
                        <p>ID: {matchedUser.id}</p>
                        <p>게임 닉네임: {matchedUser.gameName}</p>
                        <p>태그: {matchedUser.tagLine}</p>
                        <p>티어: {matchedUser.tier} {matchedUser.rank}</p>
                    </div>

                    <div className="chat-container">
                        <div className="chat-box" ref={chatBoxRef}>
                            {messages.length === 0 ? (
                                <div className="no-messages">
                                    채팅을 시작해보세요!
                                </div>
                            ) : (
                                messages.map((msg, index) => {
                                    return (
                                        <div 
                                            key={index} 
                                            className={`message ${msg.isSentByMe ? 'sent' : 'received'}`}
                                        >
                                            <div className="message-sender">
                                                {!msg.isSentByMe && `${matchedUser.gameName}`}
                                            </div>
                                            <div className="message-content">{msg.content}</div>
                                            <div className="message-time">
                                                {new Date(msg.timestamp).toLocaleTimeString()}
                                            </div>
                                        </div>
                                    );
                                })
                            )}
                        </div>

                        <div className="message-input-container">
                            <input
                                type="text"
                                value={newMessage}
                                onChange={(e) => setNewMessage(e.target.value)}
                                onKeyPress={(e) => e.key === 'Enter' && handleSendMessage()}
                                placeholder="메시지를 입력하세요..."
                                className="message-input"
                            />
                            <button 
                                onClick={handleSendMessage}
                                className="send-button"
                                disabled={!newMessage.trim()}
                            >
                                전송
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default MatchingComponent;