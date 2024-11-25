import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../../utils/api';
import './register.css';

function RegisterPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [nickname, setNickname] = useState('');

  const handleLoginClick = () => {
    navigate('/login');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
      return;
    }

    try {
      const response = await apiClient.post('/auth/signup', {
        email: email.trim(),
        password: password,
        nickname: nickname.trim(),
        role: "ROLE_USER"
      });

      console.log(response);
      alert('회원가입이 완료되었습니다.');
      navigate('/login');

    } catch (error) {
      if (error.response && error.response.data) {
        alert(error.response.data.message || '회원가입에 실패했습니다.');
      } else {
        alert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
      }
    }
  };

  return (
    <div className="register-container">
      <div className="register-logo">
        <img src="/img/logo.png" onClick={() => window.location.href = '/'} alt="웹 페이지 로고" style={{ height: '60px', width: 'auto', cursor: 'pointer' }} />
        <h2>Random Game Friends</h2>
      </div>

      <div className="register-left">
        <h2>랜덤 매칭 ID를 생성하세요.</h2>
        <p>랜덤 매칭 및 전적검색 커뮤니티에 오신것을 환영합니다. 계정을 만들고 커뮤니티를 시작하세요.</p>
      </div>

      <div className="register-right">
        <form onSubmit={handleSubmit}>
          
          <div className="input-group">
            <label htmlFor="email">이메일:</label>
            <input type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)}
            placeholder="이메일을 입력하세요" required />
          </div>
          
          <div className="input-group">
            <label htmlFor="password">비밀번호:</label>
            <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="비밀번호를 입력하세요" required />
          </div>
          
          <div className="input-group">
            <label htmlFor="confirm-password">비밀번호 확인:</label>
            <input type="password" id="confirm-password" name="confirm-password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} placeholder="비밀번호 재입력하세요" required />
          </div>

          <div className="input-group">
            <label htmlFor="nickname">닉네임:</label>
            <input type="text" id="nickname" name="nickname" value={nickname} onChange={(e) => setNickname(e.target.value)} placeholder="닉네임을 입력하세요"required />
          </div>

          <button type="submit" className="register-button">계속하기</button>
          <div className="login-return-text">이미 계정이 있으신가요? <button type="button" className="login-link-button" onClick={handleLoginClick}>로그인</button>
          </div>
        </form>
      </div>
      
      <div className="register-footer">
        <h2>강릉원주대학교 캡스톤 디자인2 5조</h2>
      </div>

    </div>
  );
}

export default RegisterPage;