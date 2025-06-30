<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Sidebar Navigation -->
<aside class="sidebar" id="sidebar">
    <nav class="sidebar-nav">
        <ul class="nav-list">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/" class="nav-link active">
                    <span class="nav-icon">🏠</span>
                    <span class="nav-text">홈</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/feed" class="nav-link">
                    <span class="nav-icon">📰</span>
                    <span class="nav-text">인기글보기</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/profile" class="nav-link">
                    <span class="nav-icon">🍍</span>
                    <span class="nav-text">프로필</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/messages" class="nav-link">
                    <span class="nav-icon">💬</span>
                    <span class="nav-text">메시지</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/notifications" class="nav-link">
                    <span class="nav-icon">🔔</span>
                    <span class="nav-text">알림</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/settings" class="nav-link">
                    <span class="nav-icon">⚙️</span>
                    <span class="nav-text">설정</span>
                </a>
            </li>
        </ul>
    </nav>
</aside>