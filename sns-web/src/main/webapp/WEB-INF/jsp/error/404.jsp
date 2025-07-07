<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<jsp:include page="/WEB-INF/jsp/include/sidebar.jsp" />

<main class="main-content">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <div class="content-wrapper container">
        <div class="error-page-content">
            <div class="construction-icon">
                🚧
            </div>
            <h1>404 - 페이지를 찾을 수 없습니다</h1>
            <h2>공사중입니다</h2>
            <p>죄송합니다. 요청하신 페이지는 현재 공사중이거나 존재하지 않습니다.</p>
            <div class="error-actions">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">홈으로 돌아가기</a>
                <button onclick="history.back()" class="btn btn-secondary">이전 페이지</button>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />

<style>
.error-page-content {
    text-align: center;
    padding: 50px 20px;
    max-width: 600px;
    margin: 0 auto;
}

.construction-icon {
    font-size: 80px;
    margin-bottom: 20px;
}

.error-page-content h1 {
    color: #333;
    margin-bottom: 10px;
}

.error-page-content h2 {
    color: #ff6b35;
    margin-bottom: 20px;
}

.error-page-content p {
    color: #666;
    margin-bottom: 30px;
    line-height: 1.6;
}

.error-actions {
    display: flex;
    gap: 15px;
    justify-content: center;
    flex-wrap: wrap;
}

.btn {
    padding: 12px 24px;
    text-decoration: none;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    font-size: 16px;
}

.btn-primary {
    background-color: #007bff;
    color: white;
}

.btn-secondary {
    background-color: #6c757d;
    color: white;
}

.btn:hover {
    opacity: 0.8;
}
</style>
