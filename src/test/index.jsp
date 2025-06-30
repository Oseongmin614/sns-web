<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="${ pageContext.request.contextPath }/resources/css/layout.css">
</head>
<body>
    <!-- Header Include -->
    
    <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <!-- Sidebar Include -->
    <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp" />
    
    <!-- Main Content -->
    <main class="main-content">
        <div class="content-wrapper">
            <!-- Post Composer -->
            <div class="card post-composer">
                <div class="card__body">
                    <div class="composer-header">
                        <img src="${pageContext.request.contextPath}/images/default-avata.png" 
                             alt="내 프로필" class="composer-avatar">
                        <textarea class="composer-textarea" placeholder="무슨 일이 일어나고 있나요?"></textarea>
                    </div>
                    <div class="composer-actions">
                       
                            <button class="composer-btn">📷 사진</button>
                           
                        <button class="btn btn--primary">게시</button>
                    </div>
                </div>
            </div>

            <!-- Feed -->
            <div class="feed">
                <!-- Sample Post 1 -->
                <article class="card post">
                    <div class="card__body">
                        <div class="post-header">
                            <div class="post-author">
                                <img src="${pageContext.request.contextPath}/resources/images/default-avatar.jpg" 
                                     alt="김철수" class="author-avatar">
                                <div class="author-info">
                                    <h4 class="author-name">김철수</h4>
                                    <time class="post-time">2시간 전</time>
                                </div>
                            </div>
                            <button class="post-menu">⋯</button>
                        </div>
                        <div class="post-content">
                            <p>안녕하세요! 새로운 SNS 플랫폼에 첫 포스트를 올립니다. 깔끔한 디자인이 마음에 드네요! 🎉</p>
                        </div>
                        <div class="post-actions">
                            <button class="action-btn">👍 좋아요</button>
                            <button class="action-btn">💬 댓글</button>
                            <button class="action-btn">📤 공유</button>
                        </div>
                    </div>
                </article>

                <!-- Sample Post 2 -->
                <article class="card post">
                    <div class="card__body">
                        <div class="post-header">
                            <div class="post-author">
                                <img src="${pageContext.request.contextPath}/resources/images/default-avatar.jpg" 
                                     alt="이영희" class="author-avatar">
                                <div class="author-info">
                                    <h4 class="author-name">이영희</h4>
                                    <time class="post-time">5시간 전</time>
                                </div>
                            </div>
                            <button class="post-menu">⋯</button>
                        </div>
                        <div class="post-content">
                            <p>오늘 날씨가 정말 좋네요! 산책하기 완벽한 날이에요 ☀️🌸</p>
                        </div>
                        <div class="post-actions">
                            <button class="action-btn">👍 좋아요</button>
                            <button class="action-btn">💬 댓글</button>
                            <button class="action-btn">📤 공유</button>
                        </div>
                    </div>
                </article>
            </div>
        </div>
    </main>

    <!-- Footer Include -->

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    <!-- JavaScript 코드 -->
    <script>
        // DOM이 로드된 후 실행
        document.addEventListener('DOMContentLoaded', function() {
            // 요소들 가져오기
            const menuToggle = document.getElementById('menuToggle');
            const sidebar = document.getElementById('sidebar');
            const navLinks = document.querySelectorAll('.nav-link');
            
            // 초기화 함수 실행
            initializeApp();
            
            // 모바일 메뉴 토글 기능
            if (menuToggle && sidebar) {
                menuToggle.addEventListener('click', function(e) {
                    e.stopPropagation();
                    menuToggle.classList.toggle('active');
                    sidebar.classList.toggle('active');
                });
                
                // 사이드바 외부 클릭 시 사이드바 닫기 (모바일에서만)
                document.addEventListener('click', function(e) {
                    if (window.innerWidth <= 768 && sidebar.classList.contains('active')) {
                        if (!sidebar.contains(e.target) && !menuToggle.contains(e.target)) {
                            sidebar.classList.remove('active');
                            menuToggle.classList.remove('active');
                        }
                    }
                });
            }
            
            // 네비게이션 링크 active 상태 관리
            navLinks.forEach(link => {
                link.addEventListener('click', function(e) {
                    e.preventDefault();
                    // 모든 링크에서 active 클래스 제거
                    navLinks.forEach(l => l.classList.remove('active'));
                    // 클릭된 링크에 active 클래스 추가
                    this.classList.add('active');
                    
                    // 모바일에서 사이드바 닫기
                    if (window.innerWidth <= 768) {
                        sidebar.classList.remove('active');
                        menuToggle.classList.remove('active');
                    }
                    
                    // 페이지 내용 시뮬레이션
                    const url = this.getAttribute('href');
                    const pageName = this.querySelector('.nav-text').textContent;
                    simulatePageChange(url, pageName);
                });
            });
            
            // 윈도우 리사이즈 이벤트
            window.addEventListener('resize', function() {
                if (window.innerWidth > 768) {
                    sidebar.classList.remove('active');
                    menuToggle.classList.remove('active');
                }
            });
        });
        
        // 앱 초기화 함수
        function initializeApp() {
            // 초기 이벤트 리스너 등록
            bindEventListeners();
            // 검색 기능 초기화
            initializeSearch();
            // 사용자 프로필 초기화
            initializeUserProfile();
        }
        
        // 이벤트 리스너 바인딩
        function bindEventListeners() {
            // 포스트 작성 기능
            const postBtn = document.querySelector('.btn--primary');
            const postComposer = document.querySelector('.composer-textarea');
            
            if (postBtn && postComposer) {
                // 기존 이벤트 리스너 제거 후 새로 등록
                postBtn.replaceWith(postBtn.cloneNode(true));
                const newPostBtn = document.querySelector('.btn--primary');v
                
                newPostBtn.addEventListener('click', function() {
                    const content = postComposer.value.trim();
                    if (content) {
                        createPost(content);
                        postComposer.value = '';
                        postComposer.style.height = '60px';
                        showNotification('포스트가 성공적으로 게시되었습니다!', 'success');
                    } else {
                        showNotification('내용을 입력해주세요.', 'warning');
                    }
                });
                
                // 텍스트 영역 자동 크기 조정
                postComposer.addEventListener('input', function() {
                    this.style.height = 'auto';
                    this.style.height = Math.max(60, this.scrollHeight) + 'px';
                });
            }
            
            // 포스트 액션 버튼들
            const actionButtons = document.querySelectorAll('.post-actions .action-btn');
            actionButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const action = this.textContent.trim();
                    handlePostAction(action, this);
                });
            });
            
            // 컴포저 버튼들
            const composerButtons = document.querySelectorAll('.composer-btn');
            composerButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const action = this.textContent.trim();
                    handleComposerAction(action);
                });
            });
        }
        
        // 검색 기능 초기화
        function initializeSearch() {
            const searchBtn = document.querySelector('.search-btn');
            const searchInput = document.querySelector('.search-input');
            
            if (searchBtn && searchInput) {
                const handleSearch = () => {
                    const query = searchInput.value.trim();
                    if (query) {
                        showNotification(`"${query}"을(를) 검색합니다...`, 'info');
                        searchInput.value = '';
                    }
                };
                
                searchBtn.addEventListener('click', handleSearch);
                searchInput.addEventListener('keypress', function(e) {
                    if (e.key === 'Enter') {
                        handleSearch();
                    }
                });
            }
        }
        
        // 사용자 프로필 초기화
        function initializeUserProfile() {
            const userProfile = document.querySelector('.user-profile');
            const actionBtns = document.querySelectorAll('.header .action-btn');
            
            if (userProfile) {
                userProfile.addEventListener('click', function() {
                    showNotification('프로필 메뉴를 열었습니다.', 'info');
                });
            }
            
            // 헤더 액션 버튼들
            actionBtns.forEach(btn => {
                btn.addEventListener('click', function() {
                    const btnText = this.textContent.trim();
                    if (btnText === '📬') {
                        showNotification('메시지를 확인합니다.', 'info');
                    } else if (btnText === '🔔') {
                        showNotification('알림을 확인합니다.', 'info');
                    }
                });
            });
        }
        
        // 포스트 생성 함수
        function createPost(content) {
            const feed = document.querySelector('.feed');
            if (!feed) return;
            
            const currentTime = new Date().toLocaleString('ko-KR', {
                hour: '2-digit',
                minute: '2-digit'
            });
            
            const postHTML = `
                <article class="card post">
                    <div class="card__body">
                        <div class="post-header">
                            <div class="post-author">
                                <img src="${pageContext.request.contextPath}/resources/images/default-avatar.jpg" 
                                     alt="나" class="author-avatar">
                                <div class="author-info">
                                    <h4 class="author-name">나</h4>
                                    <time class="post-time">방금 전</time>
                                </div>
                            </div>
                            <button class="post-menu">⋯</button>
                        </div>
                        <div class="post-content">
                            <p>${content}</p>
                        </div>
                        <div class="post-actions">
                            <button class="action-btn">👍 좋아요</button>
                            <button class="action-btn">💬 댓글</button>
                            <button class="action-btn">📤 공유</button>
                        </div>
                    </div>
                </article>
            `;
            
            feed.insertAdjacentHTML('afterbegin', postHTML);
            bindEventListeners(); // 새로운 요소들에 이벤트 리스너 재등록
        }
        
        // 포스트 액션 처리
        function handlePostAction(action, button) {
            if (action.includes('좋아요')) {
                button.classList.toggle('liked');
                showNotification('좋아요!', 'success');
            } else if (action.includes('댓글')) {
                showNotification('댓글 기능을 준비중입니다.', 'info');
            } else if (action.includes('공유')) {
                showNotification('공유 기능을 준비중입니다.', 'info');
            }
        }
        
        // 컴포저 액션 처리
        function handleComposerAction(action) {
            if (action.includes('사진')) {
                showNotification('사진 업로드 기능을 준비중입니다.', 'info');
            } else if (action.includes('동영상')) {
                showNotification('동영상 업로드 기능을 준비중입니다.', 'info');
            } else if (action.includes('기분')) {
                showNotification('기분 표현 기능을 준비중입니다.', 'info');
            } else if (action.includes('위치')) {
                showNotification('위치 태그 기능을 준비중입니다.', 'info');
            }
        }
        
        // 알림 표시 함수
        function showNotification(message, type = 'info') {
            // 기존 알림 제거
            const existingNotification = document.querySelector('.notification');
            if (existingNotification) {
                existingNotification.remove();
            }
            
            // 새 알림 생성
            const notification = document.createElement('div');
            notification.className = `notification status status--${type}`;
            notification.textContent = message;
            notification.style.cssText = `
                position: fixed;
                top: 80px;
                right: 20px;
                z-index: 10000;
                padding: 12px 20px;
                border-radius: 8px;
                font-weight: 500;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                transition: all 0.3s ease;
                transform: translateX(100%);
            `;
            
            document.body.appendChild(notification);
            
            // 애니메이션으로 표시
            setTimeout(() => {
                notification.style.transform = 'translateX(0)';
            }, 100);
            
            // 3초 후 자동 제거
            setTimeout(() => {
                notification.style.transform = 'translateX(100%)';
                setTimeout(() => {
                    if (notification.parentNode) {
                        notification.remove();
                    }
                }, 300);
            }, 3000);
        }
        
        // 페이지 변경 시뮬레이션
        function simulatePageChange(url, pageName) {
            const mainContent = document.querySelector('.content-wrapper');
            if (!mainContent) return;
            
            // 로딩 효과
            mainContent.style.opacity = '0.5';
            mainContent.style.transition = 'opacity 0.3s ease';
            
            setTimeout(() => {
                // 페이지별 컨텐츠 생성
                let content = '';
                switch(url) {
                    case '/':
                    case '${pageContext.request.contextPath}/':
                        content = getHomeContent();
                        break;
                    case '${pageContext.request.contextPath}/feed':
                        content = getFeedContent();
                        break;
                    case '${pageContext.request.contextPath}/profile':
                        content = getProfileContent();
                        break;
                    case '${pageContext.request.contextPath}/messages':
                        content = getMessagesContent();
                        break;
                    case '${pageContext.request.contextPath}/notifications':
                        content = getNotificationsContent();
                        break;
                    case '${pageContext.request.contextPath}/settings':
                        content = getSettingsContent();
                        break;
                    default:
                        content = getDefaultContent(pageName);
                }
                
                mainContent.innerHTML = content;
                mainContent.style.opacity = '1';
                
                // 새로운 컨텐츠의 이벤트 리스너 재등록
                setTimeout(() => {
                    bindEventListeners();
                }, 100);
            }, 300);
        }
        
        // 페이지별 컨텐츠 생성 함수들
        function getHomeContent() {
            return document.querySelector('.content-wrapper').innerHTML; // 현재 홈 컨텐츠 유지
        }
        
        function getFeedContent() {
            return `
                <div class="card">
                    <div class="card__body">
                        <h2>피드</h2>
                        <p>친구들의 최신 소식을 확인해보세요.</p>
                        <div class="feed">
                            <article class="card post">
                                <div class="card__body">
                                    <div class="post-header">
                                        <div class="post-author">
                                            <img src="${pageContext.request.contextPath}/resources/images/default-avatar.jpg" alt="박민수" class="author-avatar">
                                            <div class="author-info">
                                                <h4 class="author-name">박민수</h4>
                                                <time class="post-time">1시간 전</time>
                                            </div>
                                        </div>
                                        <button class="post-menu">⋯</button>
                                    </div>
                                    <div class="post-content">
                                        <p>새로운 프로젝트를 시작했습니다! 멋진 웹 애플리케이션을 만들어보겠습니다 💻✨</p>
                                    </div>
                                    <div class="post-actions">
                                        <button class="action-btn">👍 좋아요</button>
                                        <button class="action-btn">💬 댓글</button>
                                        <button class="action-btn">📤 공유</button>
                                    </div>
                                </div>
                            </article>
                            <article class="card post">
                                <div class="card__body">
                                    <div class="post-header">
                                        <div class="post-author">
                                            <img src="${pageContext.request.contextPath}/resources/images/default-avatar.jpg" alt="정수연" class="author-avatar">
                                            <div class="author-info">
                                                <h4 class="author-name">정수연</h4>
                                                <time class="post-time">3시간 전</time>
                                            </div>
                                        </div>
                                        <button class="post-menu">⋯</button>
                                    </div>
                                    <div class="post-content">
                                        <p>오늘 점심은 맛있는 파스타를 먹었어요! 정말 행복한 하루 🍝😋</p>
                                    </div>
                                    <div class="post-actions">
                                        <button class="action-btn">👍 좋아요</button>
                                        <button class="action-btn">💬 댓글</button>
                                        <button class="action-btn">📤 공유</button>
                                    </div>
                                </div>
                            </article>
                        </div>
                    </div>
                </div>
            `;
        }
        
        function getProfileContent() {
            return `
                <div class="card">
                    <div class="card__body">
                        <h2>프로필</h2>
                        <p>내 프로필을 관리하세요.</p>
                        <div class="profile-info">
                            <img src="${pageContext.request.contextPath}/resources/images/default-avatar.jpg" alt="내 프로필" style="width: 80px; height: 80px; border-radius: 50%; margin-bottom: 16px;">
                            <h3>MySNS 사용자</h3>
                            <p>안녕하세요! MySNS를 이용해주셔서 감사합니다.</p>
                        </div>
                    </div>
                </div>
            `;
        }
        
        function getMessagesContent() {
            return `
                <div class="card">
                    <div class="card__body">
                        <h2>메시지</h2>
                        <p>친구들과 대화해보세요.</p>
                        <div class="message-placeholder">
                            <p>메시지 기능을 준비중입니다. 곧 사용하실 수 있습니다!</p>
                        </div>
                    </div>
                </div>
            `;
        }
        
        function getNotificationsContent() {
            return `
                <div class="card">
                    <div class="card__body">
                        <h2>알림</h2>
                        <p>새로운 알림을 확인하세요.</p>
                        <div class="notification-placeholder">
                            <p>새로운 알림이 없습니다.</p>
                        </div>
                    </div>
                </div>
            `;
        }
        
        function getSettingsContent() {
            return `
                <div class="card">
                    <div class="card__body">
                        <h2>설정</h2>
                        <p>계정 및 개인정보 설정을 관리하세요.</p>
                        <div class="settings-placeholder">
                            <p>설정 기능을 준비중입니다. 곧 사용하실 수 있습니다!</p>
                        </div>
                    </div>
                </div>
            `;
        }
        
        function getDefaultContent(pageName) {
            return `
                <div class="card">
                    <div class="card__body">
                        <h2>${pageName}</h2>
                        <p>이 페이지는 개발 중입니다.</p>
                        <p>${pageName} 기능을 준비중입니다. 곧 사용하실 수 있습니다!</p>
                    </div>
                </div>
            `;
        }
    </script>
</body>
</html>