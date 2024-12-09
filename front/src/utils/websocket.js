import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import socketUtil from './socketconnect';

class WebSocketService {
    constructor() {
        this.stompClient = null;
        this.subscribers = new Map();
        this.reconnectAttempts = 0;
        this.maxReconnectAttempts = 5;
        this.isConnecting = false;
        this.currentChatRoomId = null;
        this.isActive = false;
    }

    async getMemberIdFromToken() {
        try {
            // GET /beforematch 호출
            const response = await fetch('http://localhost:8080/beforematch', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
                }
            });
            console.log("id: ", response);
            if (!response.ok) throw new Error('Failed to get member ID');
            return await response.json();
        } catch (error) {
            console.error('멤버 ID 조회 실패:', error);
            return null;
        }
    }

    onConnected(frame) {
        console.log('웹소켓 연결 성공:', frame);
        this.isConnecting = false;
        this.reconnectAttempts = 0;

        // memberId 조회 및 구독 설정
        this.getMemberIdFromToken().then(memberId => {
            if (memberId) {
                // 매칭 결과 구독
                this.stompClient.subscribe(`/topic/match/${memberId}`, (message) => {
                    try {
                        console.log('매칭 메시지 원본:', message.body);
                        const matchData = JSON.parse(message.body);
                        console.log('파싱된 매칭 데이터:', matchData);

                        if (matchData) {
                            // chatRoomId 저장
                            this.currentChatRoomId = matchData.chatRoomId;
                            
                            // 매칭 데이터를 구독자에게 전달
                            this.notifySubscribers('match', {
                                memberId: matchData.memberId,
                                gameName: matchData.gameName,
                                tagLine: matchData.tagLine,
                                tier: matchData.tier,
                                rank: matchData.rank,
                                chatRoomId: matchData.chatRoomId
                            });

                            // 채팅방 구독 시��
                            if (this.currentChatRoomId) {
                                console.log('채팅방 구독 시작:', this.currentChatRoomId);
                                this.subscribeToChatRoom(this.currentChatRoomId);
                            }
                        }
                    } catch (error) {
                        console.error('매칭 메시지 파싱 오류:', error, '원본 메시지:', message.body);
                    }
                });
            }
        });
    }

    // 채팅방 구독 메서드
    subscribeToChatRoom(chatRoomId) {
        console.log(`채팅방 ${chatRoomId} 구독 시작`);
        this.stompClient.subscribe(`/topic/chat/${chatRoomId}`, async (message) => {
            try {
                const chatData = JSON.parse(message.body);
                console.log('수신된 채팅 데이터:', chatData);
                
                // 현재 사용자 ID 가져오기
                const currentUserId = await this.getMemberIdFromToken();
                console.log('현재 사용자 ID:', currentUserId);
                console.log('발신자 ID:', chatData.sender);

                // 문자열로 변환하여 비교
                const isMine = String(chatData.sender) === String(currentUserId);
                console.log('ID 비교:', {
                    sender: chatData.sender,
                    currentUserId: currentUserId,
                    senderType: typeof chatData.sender,
                    currentIdType: typeof currentUserId,
                    isMine: isMine
                });

                // 채팅 데이터에 isMine 필드 추가
                const processedChatData = {
                    ...chatData,
                    isMine: isMine
                };
                console.log('처리된 채팅 데이터:', processedChatData);

                this.notifySubscribers('chat', processedChatData);
            } catch (error) {
                console.error('채팅 메시지 파싱 오류:', error);
            }
        });
    }

    // 매칭 요청 전송
    async sendMatchRequest() {
        const memberId = await this.getMemberIdFromToken();
        console.log('매칭 요청 - 멤버 ID:', memberId);
        
        if (memberId && this.stompClient?.connected) {
            console.log(`매칭 요청 전송: /app/match/${memberId}`);
            
            // memberId만 전송
            this.stompClient.publish({
                destination: `/app/match`,
                body: JSON.stringify(memberId),  // 객체가 아닌 memberId 값만 전송
                headers: {
                    'content-type': 'application/json'
                }
            });
        } else {
            console.error('매칭 요청 실패:', {
                memberId: memberId,
                connected: this.stompClient?.connected
            });
        }
    }

    // 채팅 메시지 전송
    async sendChatMessage(message) {
        if (!this.currentChatRoomId || !this.stompClient?.connected) {
            console.error('채팅방이 없거나 연결되지 않았습니다.');
            return;
        }

        try {
            const sender = await this.getMemberIdFromToken();
            console.log('채팅 메시지 전송:', {
                chatroomid: this.currentChatRoomId,
                sender: sender,
                message: message
            });

            this.stompClient.publish({
                destination: '/app/chat/message',
                body: JSON.stringify({
                    chatroomid: this.currentChatRoomId,
                    sender: sender,
                    message: message
                }),
                headers: {
                    'content-type': 'application/json'
                }
            });
        } catch (error) {
            console.error('채팅 메시지 전송 실패:', error);
        }
    }

    onError(error) {
        console.error('웹소켓 연결 오류:', error);
        this.isConnecting = false;
        this.handleReconnect();
    }

    connect() {
        if (this.isConnecting || this.isActive) return;
        this.isConnecting = true;
        this.isActive = true;

        const token = localStorage.getItem('accessToken');
        const wsUrl = process.env.REACT_APP_WS_URL || 'http://localhost:8080/ws';
        console.log('웹소켓 연결 URL:', wsUrl);
        console.log('토큰:', token);

        try {
            if (this.stompClient) {
                this.stompClient.deactivate();
                this.stompClient = null;
            }

            const socket = new SockJS(wsUrl);
            console.log('SockJS 인스턴스 생성됨:', socket);

            this.stompClient = new Client({
                webSocketFactory: () => socket,
                connectHeaders: {
                    'Authorization': `Bearer ${token}`
                },
                debug: (str) => {
                    console.log('STOMP 디버그:', str);
                },
                reconnectDelay: 5000,
                heartbeatIncoming: 4000,
                heartbeatOutgoing: 4000,
            });

            this.stompClient.onConnect = (frame) => {
                console.log('STOMP 연결 성공:', frame);
                this.onConnected(frame);
            };

            this.stompClient.onStompError = (frame) => {
                console.error('STOMP 에러:', frame);
                this.onError(frame);
            };

            this.stompClient.onWebSocketError = (event) => {
                console.error('웹소켓 에러:', event);
                this.isActive = false;
            };

            this.stompClient.onWebSocketClose = (event) => {
                console.log('웹소켓 연결 닫힘:', event);
                this.isConnecting = false;
                this.isActive = false;
            };

            console.log('STOMP 클라이언트 활성화 시도...');
            this.stompClient.activate();
        } catch (error) {
            console.error('웹소켓 연결 중 예외 발생:', error);
            this.isConnecting = false;
            this.isActive = false;
            this.handleReconnect();
        }
    }

    handleReconnect() {
        if (this.reconnectAttempts < this.maxReconnectAttempts) {
            this.reconnectAttempts++;
            console.log(`재연결 시도 ${this.reconnectAttempts}/${this.maxReconnectAttempts}`);
            setTimeout(() => this.connect(), 3000);
        }
    }

    subscribe(topic, callback) {
        if (!this.subscribers.has(topic)) {
            this.subscribers.set(topic, new Set());
        }
        this.subscribers.get(topic).add(callback);

        return () => {
            const callbacks = this.subscribers.get(topic);
            if (callbacks) {
                callbacks.delete(callback);
                if (callbacks.size === 0) {
                    this.subscribers.delete(topic);
                }
            }
        };
    }

    notifySubscribers(topic, data) {
        if (this.subscribers.has(topic)) {
            this.subscribers.get(topic).forEach(callback => callback(data));
        }
    }

    sendMessage(destination, data) {
        if (this.stompClient && this.stompClient.connected) {
            this.stompClient.publish({
                destination: `/app/${destination}`,
                body: JSON.stringify(data)
            });
        } else {
            console.error('STOMP 클라이언트가 연결되어 있지 않습니다');
        }
    }

    disconnect() {
        console.log('웹소켓 연결 종료 시도');
        this.isActive = false;
        if (this.stompClient) {
            this.stompClient.deactivate();
            this.stompClient = null;
        }
        this.currentChatRoomId = null;
        this.subscribers.clear();
        this.reconnectAttempts = 0;
        this.isConnecting = false;
    }
}

const websocketService = new WebSocketService();
export default websocketService;
