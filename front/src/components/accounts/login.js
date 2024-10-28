/* login.js */
import config from "../../config.js";
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './login.css';

function LoginPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const SERVER_URL = config.SERVER_URL;  


  const handleRegisterClick = () => {
    navigate('/register');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`${SERVER_URL}/auth/signup`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        navigate('/');
      } else {
        // Handle login error
        console.error('로그인 실패');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-logo">
        <img src="/img/logo.png" onClick={() => window.location.href = '/'} alt="웹 페이지 로고" style={{ height: '60px', width: 'auto', cursor: 'pointer' }} />
        <h2>Random Game Friends</h2>
      </div>

      <div className="login-contents">
        <h2>로그인</h2>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label htmlFor="email">이메일:</label>
            <input type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div className="input-group">
            <label htmlFor="password">비밀번호:</label>
            <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
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