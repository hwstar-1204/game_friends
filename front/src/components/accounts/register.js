import React from 'react';
import { useNavigate } from 'react-router-dom';
import './register.css';

function RegisterPage() {
  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate('/login');
  };

  return (
    <div className="register-container">
      <div className="register-logo">
        <img src="/img/logo.png" onClick={() => window.location.href = '/'} alt="웹 페이지 로고" style={{ height: '40px', width: 'auto', cursor: 'pointer' }} />
      </div>
      <div className="register-left">
        <h2>랜덤 매칭 ID를 생성하세요.</h2>
        <p>랜덤 매칭 및 전적검색 커뮤니티에 오신것을 환영합니다. 계정을 만들고 커뮤니티를 시작하세요.</p>
      </div>
      <div className="register-right">
        <form>
          <div className="input-group">
            <label htmlFor="username">아이디:</label>
            <input type="text" id="username" name="username" required />
          </div>
          <div className="input-group">
            <label htmlFor="password">비밀번호:</label>
            <input type="password" id="password" name="password" required />
          </div>
          <div className="input-group">
            <label htmlFor="confirm-password">비밀번호 확인:</label>
            <input type="password" id="confirm-password" name="confirm-password" required />
          </div>
          <button type="submit" className="register-button">계속하기</button>
          <div className="login-return-text">이미 계정이 있으신가요? <button type="button" className="login-link-button" onClick={handleLoginClick}>로그인</button></div>
        </form>
      </div>
      <div className="register-footer">
        <h2>강릉원주대학교 캡스톤 디자인2 5조</h2>
      </div>
    </div>
  );
}

export default RegisterPage;