// chat.js

import React, { useState, useEffect, useRef } from 'react';
import './chat.css';

function ChatWindow({ friendName, friendTier }) {
  const [messages, setMessages] = useState([{
    content: 'ㅎㅇ',
    timestamp: '오전 9:43',
    type: 'received'
  }, {
    content: 'ㅎㅇㅎㅇ',
    timestamp: '오전 9:45',
    type: 'sent'
  }]);
  const [inputValue, setInputValue] = useState('');

  // 프로필 이미지 URL 정의 (예시로 빈 문자열을 사용할 경우 기본 회색 원이 보임)
  const friendImageUrl = ''; // 이미지가 없을 때 빈 문자열

  const handleSendMessage = () => {
    if (inputValue.trim() === '') return;
    const newMessage = {
      content: inputValue,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      type: 'sent'
    };
    setMessages([...messages, newMessage]);
    setInputValue('');
  };

  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  return (
    <div className="chat-container">
      <div className="chat-header">
        <div>
          <div className="profile-image" style={{ backgroundImage: `url(${friendImageUrl || ''})` }}></div>
          <div className="profile-details">
            <span>{friendName}</span>
          </div>
        </div>
        <div className={`friend-tier tier-${friendTier}`}></div>
      </div>
      <div className="chat-messages">
        {messages.map((message, index) => (
          <div key={index} className={`chat-message ${message.type}`}>
            <div className="message-content">
              {message.content}
              <div className="message-timestamp">{message.timestamp}</div>
            </div>
          </div>
        ))}
        <div ref={messagesEndRef} />
      </div>
      <div className="chat-footer">
        <input
          type="text"
          className="chat-input"
          placeholder="메시지 입력"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          onKeyPress={(e) => {
            if (e.key === 'Enter') handleSendMessage();
          }}
        />
        <button className="chat-send-button" onClick={handleSendMessage}>➤</button>
      </div>
    </div>
  );
}

export default ChatWindow;
