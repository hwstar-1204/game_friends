// src/components/modals/accountChange.js
import React, { useState } from 'react';
import './accountChange.css';

function AccountChangeModal({ type, onClose, onSubmit, currentPassword }) {
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = () => {
    if (type === 'nickname') {
      if (nickname.trim() === '' || password.trim() === '') {
        alert('닉네임과 비밀번호를 모두 입력해주세요.');
        return;
      }
      if (password !== currentPassword) {
        alert('현재 비밀번호가 일치하지 않습니다.');
        return;
      }
      onSubmit({ nickname });
      onClose();
    } else if (type === 'password') {
      if (password.trim() === '' || newPassword.trim() === '' || confirmPassword.trim() === '') {
        alert('모든 필드를 입력해주세요.');
        return;
      }
      if (password !== currentPassword) {
        alert('현재 비밀번호가 일치하지 않습니다.');
        return;
      }
      if (newPassword !== confirmPassword) {
        alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
        return;
      }
      onSubmit({ newPassword });
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
              type="text"
              placeholder="새 닉네임 입력"
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
            />
            <input
              type="password"
              placeholder="현재 비밀번호 입력"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </>
        )}
        {type === 'password' && (
          <>
            <input
              type="password"
              placeholder="현재 비밀번호 입력"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <input
              type="password"
              placeholder="새 비밀번호 입력"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
            />
            <input
              type="password"
              placeholder="새 비밀번호 확인"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
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
