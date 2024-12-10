/* login.js */
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './login.css';
import { login } from '../../utils/accontApi';
import { getUserInfo, getSummonerInfoByNickname } from '../../utils/utilsApi';

function LoginPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');


  const handleRegisterClick = () => {
    navigate('/register');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login(email, password);
      localStorage.setItem('accessToken', response.accessToken);
      localStorage.setItem('tokenExpiresIn', response.tokenExpiresIn);

      const userInfo = await getUserInfo();
      localStorage.setItem('email', userInfo.email || '');
      localStorage.setItem('nickname', userInfo.nickname || '');

      if (!userInfo?.nickname) {
        const summonerInfo = await getSummonerInfoByNickname(userInfo.nickname);
        localStorage.setItem('gameName', summonerInfo.gameName || '');
        localStorage.setItem('tagLine', summonerInfo.tagLine || '');
      }

      // storeUserInfo();
      navigate('/');
      
    } catch (error) {
      alert('로그인 실패');
      console.error('로그인 오류:', error);
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
            <input type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="이메일을 입력하세요" required />
          </div>
          <div className="input-group">
            <label htmlFor="password">비밀번호:</label>
            <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="비밀번호를 입력하세요" required />
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