<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 - MySNS</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(135deg, var(--background-color) 0%, var(--hover-color) 100%);
        }
        .auth-container {
            max-width: 400px;
            width: 100%;
            animation: fadeIn 0.5s ease-in-out;
        }
        .auth-card {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: var(--shadow-heavy);
        }
        .form-group {
            margin-bottom: 20px;
            position: relative;
        }
        .form-control {
            width: 100%;
            padding: 12px 12px 12px 40px;
            border: 1px solid var(--border-color);
            border-radius: var(--border-radius);
            font-size: 16px;
            transition: var(--transition);
        }
        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(0, 204, 0, 0.1);
        }
        .form-icon {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: var(--text-muted);
        }
        .validation-message {
            font-size: 12px;
            color: #E74C3C;
            margin-top: 5px;
            display: none;
        }
        .profile-preview {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid var(--border-color);
            margin: 0 auto 16px;
            display: block;
        }
        .form-file {
            text-align: center;
            margin-bottom: 16px;
        }
        .form-file input[type="file"] {
            display: none;
        }
        .form-file label {
            cursor: pointer;
            color: var(--primary-color);
            font-weight: 500;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <div class="auth-container">
        <div class="card auth-card">
            <div class="card__body">
                <div class="text-center mb-16">
                    <h1 class="logo" style="font-size: 36px;">🍍Ananas</h1>
                   
                </div>

                <!-- 프로필 이미지 미리보기 -->
                <img id="profilePreview" src="${pageContext.request.contextPath}/images/default-avata.png" alt="프로필" class="profile-preview">

                <form id="signupForm" action="${pageContext.request.contextPath}/user/signup" method="post" enctype="multipart/form-data">
                    <!-- 프로필 사진 업로드 -->
                    <div class="form-file">
                        <input type="file" name="profileImage" id="profileImage" accept="image/*">
                        <label for="profileImage"><i class="fa fa-image"></i> 프로필 사진 선택</label>
                    </div>

                    <div class="form-group">
                        <i class="fa fa-envelope form-icon"></i>
                        <input type="email" id="email" name="email" class="form-control" placeholder="이메일 주소" required>
                    </div>

                    <div class="form-group">
                        <i class="fa fa-user form-icon"></i>
                        <input type="text" id="nickname" name="nickname" class="form-control" placeholder="닉네임" required>
                    </div>

                    <div class="form-group">
                        <i class="fa fa-lock form-icon"></i>
                        <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호" required>
                    </div>

                    <div class="form-group">
                        <i class="fa fa-check-circle form-icon"></i>
                        <input type="password" id="password-confirm" name="password-confirm" class="form-control" placeholder="비밀번호 확인" required>
                        <div id="password-feedback" class="validation-message">비밀번호가 일치하지 않습니다.</div>
                    </div>

                    <button type="submit" class="btn btn--primary" style="width: 100%;">회원가입</button>
                </form>

                <div class="text-center mt-20">
                    <p class="text-muted">이미 계정이 있으신가요? <a href="${pageContext.request.contextPath}/user/login" style="color: var(--primary-color); font-weight: 500;">로그인</a></p>
                </div>
            </div>
        </div>
    </div>
	
    <script>
        const signupForm = document.getElementById('signupForm');
        const password = document.getElementById('password');
        const passwordConfirm = document.getElementById('password-confirm');
        const passwordFeedback = document.getElementById('password-feedback');
        const profileImageInput = document.getElementById('profileImage');
        const profilePreview = document.getElementById('profilePreview');

        signupForm.addEventListener('submit', function(event) {
            if (password.value !== passwordConfirm.value) {
                event.preventDefault();
                passwordFeedback.style.display = 'block';
                passwordConfirm.focus();
                passwordConfirm.style.borderColor = '#E74C3C';
            }
        });

        passwordConfirm.addEventListener('input', function() {
            if (password.value === passwordConfirm.value) {
                passwordFeedback.style.display = 'none';
                passwordConfirm.style.borderColor = 'var(--primary-color)';
            } else {
                passwordFeedback.style.display = 'block';
                passwordConfirm.style.borderColor = '#E74C3C';
            }
        });

        // 프로필 이미지 미리보기
        profileImageInput.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    profilePreview.src = e.target.result;
                };
                reader.readAsDataURL(file);
            } else {
                profilePreview.src = '${pageContext.request.contextPath}/images/default-avata.png';
            }
        });
    </script>
</body>
</html>
