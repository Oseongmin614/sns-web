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
            <!-- Logo -->
            <h1 class="logo">Ananas</h1>
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
                            <img src="${sessionScope.user.profileImgUrl}" 
                                 alt="프로필" class="profile-img">
                            <span class="profile-name">${sessionScope.user.displayName}</span>
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
function toggleProfileMenu() {
    const dropdown = document.getElementById('profileDropdown');
    dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
}

function logout() {
    if (confirm('로그아웃 하시겠습니까?')) {
        $.ajax({
            url: '${pageContext.request.contextPath}/user/logout',
            type: 'POST',
            success: function(response) {
                if (response.success) {
                    window.location.href = '${pageContext.request.contextPath}/user/login';
                } else {
                    alert(response.message);
                }
            },
            error: function() {
                alert('로그아웃 처리 중 오류가 발생했습니다.');
            }
        });
    }
}

// 페이지 외부 클릭 시 드롭다운 닫기
document.addEventListener('click', function(e) {
    const profileMenu = document.querySelector('.user-profile');
    const dropdown = document.getElementById('profileDropdown');
    
    if (!profileMenu.contains(e.target)) {
        dropdown.style.display = 'none';
    }
});
</script>
