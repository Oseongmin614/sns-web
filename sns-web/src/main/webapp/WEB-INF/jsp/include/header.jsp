<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Header -->
<header class="header">
    <div class="header-content">
        <div class="header-left">
            <!-- Mobile Menu Toggle -->
            <button class="menu-toggle" id="menuToggle">
                <span></span>
                <span></span>
                <span></span>
            </button>
            <!-- Logo --><a href="${pageContext.request.contextPath}/" class="logo-link">
            <h1 class="logo">🍍Ananas</h1>
            </a>
        </div>
        
        <!-- Search Bar (Desktop) -->
        <div class="header-center">
            <div class="search-box">
                <input type="text" class="search-input" placeholder="검색어를 입력하세요...">
                <button class="search-btn">🔍</button>
            </div>
        </div>
        
        <!-- User Actions -->
        <div class="header-right">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <!-- 로그인된 상태 -->
                    <div class="user-actions">
                        <button class="action-btn" title="메시지">📬</button>
                        <button class="action-btn" title="알림">🔔</button>
                        
                        <!-- User Profile -->
                        <div class="user-profile" onclick="toggleProfileMenu()">
                        	<c:url value="${not empty sessionScope.user.profileImg ? sessionScope.user.profileImg : '/images/default-avata.png'}" var="profileImageSrc" />
                           
                            <img src="${profileImageSrc}" alt="프로필" class="profile-img">
                            <span class="profile-name">${sessionScope.user.nickname}</span>
                        </div>
                        
                        <!-- Profile Dropdown Menu -->
                        <div class="profile-dropdown" id="profileDropdown" style="display: none;">
                            <a href="${pageContext.request.contextPath}/user/profile" class="dropdown-item">
                                <span>👤</span> 마이페이지
                            </a>
                            <a href="${pageContext.request.contextPath}/user/settings" class="dropdown-item">
                                <span>⚙️</span> 설정
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="#" onclick="logout()" class="dropdown-item">
                                <span>🚪</span> 로그아웃
                            </a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- 로그인되지 않은 상태 -->
                    <div class="auth-buttons">
                        <a href="${pageContext.request.contextPath}/user/login" class="btn btn--secondary">로그인</a>
                        <a href="${pageContext.request.contextPath}/user/signup" class="btn btn--primary">회원가입</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

<style>
.logo-link {
    text-decoration: none;
}
.auth-buttons {
    display: flex;
    gap: 8px;
}

.profile-dropdown {
    position: absolute;
    top: 100%;
    right: 0;
    background: var(--card-background);
    border: 1px solid var(--border-color);
    border-radius: var(--border-radius);
    box-shadow: var(--shadow-medium);
    min-width: 200px;
    z-index: 1001;
    margin-top: 8px;
}

.dropdown-item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 12px 16px;
    text-decoration: none;
    color: var(--text-primary);
    transition: var(--transition);
}

.dropdown-item:hover {
    background-color: var(--hover-color);
    color: var(--primary-color);
}

.dropdown-divider {
    height: 1px;
    background-color: var(--border-color);
    margin: 4px 0;
}
</style>

<script>
// jQuery가 필요합니다. 만약 없다면, ванильный JS로 수정해야 합니다.
// 로그아웃 기능은 POST 방식으로 처리하는 것이 CSRF 공격에 더 안전합니다.
function logout() {
    if (confirm('로그아웃 하시겠습니까?')) {
        // 동적으로 form을 생성하여 POST 요청
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '${pageContext.request.contextPath}/user/logout';
        document.body.appendChild(form);
        form.submit();
    }
}

function toggleProfileMenu() {
    const dropdown = document.getElementById('profileDropdown');
    const isHidden = dropdown.style.display === 'none' || dropdown.style.display === '';
    dropdown.style.display = isHidden ? 'block' : 'none';
}

// 페이지 외부 클릭 시 드롭다운 닫기
document.addEventListener('click', function(e) {
    const profileMenu = document.querySelector('.user-profile');
    const dropdown = document.getElementById('profileDropdown');
    
    // 프로필 메뉴 자체를 클릭했을 때는 이 이벤트가 닫지 않도록 함
    if (profileMenu && !profileMenu.contains(e.target)) {
        if(dropdown) dropdown.style.display = 'none';
    }
});
</script>
