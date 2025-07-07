<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 - MySNS</title>
    
    <!-- 공통 CSS 링크 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <!-- Font Awesome 아이콘 라이브러리 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- 로그인 페이지 전용 스타일 -->
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
    </style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <div class="auth-container">
        <div class="card auth-card">
            <div class="card__body">
                <div class="text-center mb-16">
                    <h1 class="logo" style="font-size: 36px;">🍍Ananas</h1>
                    <p class="text-secondary">로그인하여 친구들의 소식을 확인하세요.</p>
                </div>
                
                <form action="${pageContext.request.contextPath}/user/login" method="post">
                    <!-- 에러 메시지 표시 -->
                    <c:if test="${not empty error}">
                        <div class="notification status--error mb-16" style="position: static; text-align: center;">
                            ${error}
                        </div>
                    </c:if>

                    <div class="form-group">
                        <i class="fa fa-envelope form-icon"></i>
                        <input type="email" id="email" name="email" class="form-control" placeholder="이메일 주소" required>
                    </div>

                    <div class="form-group">
                        <i class="fa fa-lock form-icon"></i>
                        <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호" required>
                    </div>

                    <button type="submit" class="btn btn--primary" style="width: 100%;">로그인</button>
                </form>

                <div class="text-center mt-20">
                    <p class="text-muted">계정이 없으신가요? <a href="${pageContext.request.contextPath}/user/signup" style="color: var(--primary-color); font-weight: 500;">회원가입</a></p>
                </div>
            </div>
        </div>
    </div>
    
</body>
</html>
