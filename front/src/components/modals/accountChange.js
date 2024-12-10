// src/components/modals/accountChange.js
import React, { useState } from 'react';
import './accountChange.css';

function AccountChangeModal({ type, onClose, onSubmit}) {
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [prePassword, setPrePassword] = useState('');
  const [newPassword, setNewPassword] = useState('');

  const handleSubmit = () => {
    if (type === 'nickname') {
      if (nickname.trim() === '' || email.trim() === '') {
        alert('닉네임과 이메일을 모두 입력해주세요.');
        return;
      }
      onSubmit({ email,nickname });
      onClose();

    } else if (type === 'password') {
      if (email.trim() === '' || prePassword.trim() === '' || newPassword.trim() === '') {
        alert('모든 필드를 입력해주세요.');
        return;
      }
      onSubmit({ email, prePassword, newPassword });
      onClose();
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <h3>{type === 'nickname' ? '닉네임 변경' : '비밀번호 변경'}</h3>
        {type === 'nickname' && (
          <>
            <input
              type="email"
              placeholder="이메일 입력"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="text"
              placeholder="새 닉네임 입력"
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
            />
          </>
        )}
        {type === 'password' && (
          <>
            <input
              type="email"
              placeholder="이메일 입력"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="password"
              placeholder="현재 비밀번호 입력"
              value={prePassword}
              onChange={(e) => setPrePassword(e.target.value)}
            />
            <input
              type="password"
              placeholder="새 비밀번호 확인"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
            />
          </>
        )}
        <div className="modal-buttons">
          <button onClick={onClose}>취소</button>
          <button onClick={handleSubmit}>확인</button>
        </div>
      </div>
    </div>
  );
}

export default AccountChangeModal;
