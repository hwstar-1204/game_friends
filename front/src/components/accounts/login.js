/* login.js */

import React from 'react';
import { useNavigate } from 'react-router-dom';
import './login.css';

function LoginPage() {
  const navigate = useNavigate();

  const handleRegisterClick = () => {
    navigate('/register');
  };

  return (
    <div className="login-container">
      <div className="login-logo">
        <img src="/img/logo.png" onClick={() => window.location.href = '/'} alt="웹 페이지 로고" style={{ height: '60px', width: 'auto', cursor: 'pointer' }} />
        <h2>Random Game Friends</h2>
      </div>

      <div className="login-contents">
        <h2>로그인</h2>
        <form>
          <div className="input-group">
            <label htmlFor="username">아이디:</label>
            <input type="text" id="username" name="username" placeholder="이메일 아이디를 입력하세요." required />
          </div>
          <div className="input-group">
            <label htmlFor="password">비밀번호:</label>
            <input type="password" id="password" name="password" required />
          </div>
          <button type="submit" className="login-button">로그인</button>
          <button type="button" className="register-button" onClick={handleRegisterClick}>회원가입</button>
        </form>

      </div>
      <div className="login-footer">
        <h2>강릉원주대학교 캡스톤 디자인2 5조</h2>
      </div>
    </div>
  );
}

export default LoginPage;