<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("pageTitle", "회원가입 - MySNS");
%>
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<jsp:include page="/WEB-INF/jsp/include/sidebar.jsp" />

<div class="container" style="max-width:400px; margin:60px auto;">
    <div class="card">
        <div class="card__body">
            <h2 class="text-center mb-16">회원가입</h2>
            <form method="post" action="${pageContext.request.contextPath}/signup.do">
                <div class="mb-16">
                    <label for="email" class="mb-8">이메일</label>
                    <input type="email" id="email" name="email" class="search-input" required placeholder="이메일을 입력하세요">
                </div>
                <div class="mb-16">
                    <label for="password" class="mb-8">비밀번호</label>
                    <input type="password" id="password" name="password" class="search-input" required placeholder="비밀번호를 입력하세요">
                </div>
                <div class="mb-16">
                    <label for="confirmPassword" class="mb-8">비밀번호 확인</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="search-input" required placeholder="비밀번호를 다시 입력하세요">
                </div>
                <!-- 닉네임 등 추가 정보가 필요하다면 여기에 추가 -->
                <button type="submit" class="btn btn--primary w-100 mb-16">회원가입</button>
            </form>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/login.jsp" class="text-muted">이미 계정이 있으신가요? 로그인</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
