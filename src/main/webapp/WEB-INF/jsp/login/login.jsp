<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MySNS - 로그인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .login-container {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, var(--background-color) 0%, var(--hover-color) 100%);
            padding: 20px;
        }
        
        .login-card {
            background: var(--card-background);
            border-radius: 16px;
            box-shadow: var(--shadow-heavy);
            padding: 40px;
            width: 100%;
            max-width: 420px;
            border: 1px solid var(--border-color);
        }
        
        .login-header {
            text-align: center;
            margin-bottom: 32px;
        }
        
        .login-logo {
            font-size: 32px;
            font-weight: bold;
            color: var(--primary-color);
            margin-bottom: 8px;
        }
        
        .login-subtitle {
            color: var(--text-secondary);
            font-size: 16px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text-primary);
        }
        
        .form-input {
            width: 100%;
            padding: 14px 16px;
            border: 1px solid var(--border-color);
            border-radius: var(--border-radius);
            font-size: 16px;
            background-color: var(--card-background);
            transition: var(--transition);
            outline: none;
        }
        
        .form-input:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(0, 204, 0, 0.1);
        }
        
        .form-input.error {
            border-color: #E74C3C;
            box-shadow: 0 0 0 3px rgba(231, 76, 60, 0.1);
        }
        
        .error-message {
            color: #E74C3C;
            font-size: 14px;
            margin-top: 4px;
        }
        
        .login-options {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
        }
        
        .remember-me {
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
            color: var(--text-secondary);
        }
        
        .forgot-password {
            color: var(--primary-color);
            text-decoration: none;
            font-size: 14px;
            transition: var(--transition);
        }
        
        .forgot-password:hover {
            color: var(--secondary-color);
        }
        
        .btn-login {
            width: 100%;
            padding: 14px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: var(--border-radius);
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: var(--transition);
            margin-bottom: 24px;
            box-shadow: var(--shadow-light);
        }
        
        .btn-login:hover {
            background-color: var(--secondary-color);
            transform: translateY(-2px);
            box-shadow: var(--shadow-medium);
        }
        
        .btn-login:disabled {
            background-color: var(--text-muted);
            cursor: not-allowed;
            transform: none;
        }
        
        .signup-link {
            text-align: center;
            margin-top: 24px;
            color: var(--text-secondary);
        }
        
        .signup-link a {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
        }
        
        .signup-link a:hover {
            color: var(--secondary-color);
        }
        
        .loading {
            display: none;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }
        
        .loading-spinner {
            width: 20px;
            height: 20px;
            border: 2px solid var(--border-color);
            border-top: 2px solid var(--primary-color);
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }
        
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <div class="login-logo">MySNS</div>
                <div class="login-subtitle">소셜 네트워크에 오신 것을 환영합니다</div>
            </div>
            
            <form id="loginForm" method="post" action="${pageContext.request.contextPath}/user/login">
                <div class="form-group">
                    <label for="email" class="form-label">이메일</label>
                    <input type="email" id="email" name="email" class="form-input" 
                           placeholder="이메일을 입력하세요" required>
                    <div class="error-message" id="emailError"></div>
                </div>
                
                <div class="form-group">
                    <label for="password" class="form-label">비밀번호</label>
                    <input type="password" id="password" name="password" class="form-input" 
                           placeholder="비밀번호를 입력하세요" required>
                    <div class="error-message" id="passwordError"></div>
                </div>
                
                <div class="login-options">
                    <label class="remember-me">
                        <input type="checkbox" name="rememberMe">
                        <span>로그인 상태 유지</span>
                    </label>
                    <a href="#" class="forgot-password">비밀번호 찾기</a>
                </div>
                
                <button type="submit" class="btn-login">
                    <span class="login-text">로그인</span>
                    <div class="loading">
                        <div class="loading-spinner"></div>
                        <span>로그인 중...</span>
                    </div>
                </button>
            </form>
            
            <div class="signup-link">
                아직 회원이 아니신가요? <a href="${pageContext.request.contextPath}/user/signup">회원가입</a>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            // 이메일 유효성 검사
            $('#email').on('blur', function() {
                const email = $(this).val();
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                
                if (email && !emailRegex.test(email)) {
                    $(this).addClass('error');
                    $('#emailError').text('올바른 이메일 형식이 아닙니다.');
                } else {
                    $(this).removeClass('error');
                    $('#emailError').text('');
                }
            });
            
            // 로그인 폼 제출
            $('#loginForm').on('submit', function(e) {
                e.preventDefault();
                
                const email = $('#email').val();
                const password = $('#password').val();
                
                // 유효성 검사
                if (!email || !password) {
                    alert('이메일과 비밀번호를 모두 입력해주세요.');
                    return;
                }
                
                // 로딩 상태 표시
                $('.login-text').hide();
                $('.loading').show();
                $('.btn-login').prop('disabled', true);
                
                // AJAX 로그인 요청
                $.ajax({
                    url: '${pageContext.request.contextPath}/user/login',
                    type: 'POST',
                    data: {
                        email: email,
                        password: password,
                        rememberMe: $('input[name="rememberMe"]').is(':checked')
                    },
                    success: function(response) {
                        if (response.success) {
                            window.location.href = '${pageContext.request.contextPath}/';
                        } else {
                            alert(response.message || '로그인에 실패했습니다.');
                        }
                    },
                    error: function() {
                        alert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                    },
                    complete: function() {
                        $('.login-text').show();
                        $('.loading').hide();
                        $('.btn-login').prop('disabled', false);
                    }
                });
            });
        });
    </script>
</body>
</html>
