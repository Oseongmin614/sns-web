<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<jsp:include page="/WEB-INF/jsp/include/sidebar.jsp" />

<main class="main-content">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/layout.css">
    <div class="content-wrapper container">
        <!-- 글 작성 영역 -->
        <form id="postWriteForm" action="<c:url value='/post/write'/>" method="post" class="post-composer">
            <div class="composer-header">
                <img src="${pageContext.request.contextPath}/images/default-avata.png"
                     alt="Avatar" class="composer-avatar">
                <input type="text" id="title" name="title" class="composer-input" placeholder="게시글 제목을 입력하세요." required>
            </div>
            <div class="composer-body">
                <textarea id="content" name="content" class="composer-textarea" placeholder="무슨 생각을 하고 계신가요?" required></textarea>
            </div>
            <div class="composer-actions">
                <div class="composer-options">
                    <button type="button" class="composer-btn">📷 사진</button>
                    <button type="button" class="composer-btn">😊 기분</button>
                    <button type="button" class="composer-btn">📍 위치</button>
                </div>
                <button type="submit" class="btn btn--primary">게시</button>
            </div>
        </form>

        <!-- 무한 스크롤 피드 영역 -->
        <section class="feed" id="feed">
            <%-- 여기로 AJAX로 불러온 게시글이 추가됩니다 --%>
        </section>
        <div id="feed-loading" style="text-align:center; display:none; margin:20px 0;">
            <img src="${pageContext.request.contextPath}/images/loading.png"
                 alt="로딩중..." style="width:32px;">
        </div>
        <div id="feed-end"
             style="text-align:center; display:none; margin:20px 0; color:#aaa;">
            더 이상 게시글이 없습니다.
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    let lastId = null, loading = false, endOfFeed = false;

        // 게시글 작성 폼 제출 처리
        $('#postWriteForm').on('submit', function(e) {
            e.preventDefault(); // 폼 기본 제출 방지

            var formData = $(this).serialize();

            $.ajax({
                url: $(this).attr('action'),
                type: 'POST',
                data: formData,
                success: function(response) {
                    if (response === 'success') {
                        alert('게시글이 성공적으로 작성되었습니다.');
                        // 게시글 작성 후 피드 새로고침 또는 새 게시글 추가
                        window.location.reload(); 
                    } else if (response === 'unauthorized') {
                        alert('로그인이 필요합니다.');
                        window.location.href = '<c:url value="/user/login"/>';
                    } else {
                        alert('게시글 작성에 실패했습니다.');
                    }
                },
                error: function(xhr, status, error) {
                    if (xhr.status === 401) { // Unauthorized
                        alert('로그인이 필요합니다.');
                        window.location.href = '<c:url value="/user/login"/>';
                    } else {
                        alert('서버 오류가 발생했습니다: ' + xhr.responseText);
                    }
                }
            });
        });

    function loadFeedChunk() {
        if (loading || endOfFeed) return;
        loading = true;
        $('#feed-loading').show();

        $.ajax({
            url: '${pageContext.request.contextPath}/post/feedChunk',
            method: 'GET',
            data: { lastId: lastId, limit: 10 },
            dataType: 'json',
            success: function(response) {
                $('#feed-loading').hide();
                var posts = response.posts;  
                if (!Array.isArray(posts) || posts.length === 0) {
                  endOfFeed = true;
                  $('#feed-end').show();
                  return;
                }
                <%--
                if (posts.length === 0) {
                    endOfFeed = true;
                    $('#feed-end').show();
                    return;
                }
                posts.forEach(function(p) {
                    const defaultAvatar = '${pageContext.request.contextPath}/images/default-avata.png';
                    const profileImg   = p.profileImg ? p.profileImg : defaultAvatar;
                    const imgTag       = p.imageUrl    ? `<img src="${p.imageUrl}"
                                                   alt="Post image"
                                                   class="post-image">` : '';
                    const postHtml = `
                        <article class="post">
                          <header class="post-header">
                            <div class="post-author">
                              <img src="${profileImg}" alt="Avatar" class="author-avatar">
                              <div class="author-info">
                                <h3 class="author-name">${p.nickname}</h3>
                                <time class="post-time">${p.createdAtStr}</time>
                              </div>
                            </div>
                            <button class="post-menu">⋮</button>
                          </header>
                          <div class="post-content">
                            <p>${p.content}</p>
                            ${imgTag}
                          </div>
                          <footer class="post-actions">
                            <button class="action-btn${p.liked ? ' liked' : ''}">
                              👍 ${p.likeCount}
                            </button>
                            <button class="action-btn">💬 댓글</button>
                            <button class="action-btn">💾 공유</button>
                          </footer>
                        </article>`;
                    $('#feed').append(postHtml);
                    lastId = p.postId;
                });
                --%>
                posts.forEach(function(p) {
                    var defaultAvatar = 'http://localhost:8080/sns-web/' + '/images/default-avata.png';
                    var profileImg = p.profileImg ? p.profileImg : defaultAvatar;
                    var imgTag = p.imageUrl
                        ? '<img src="' + p.imageUrl + '" ' +
                          'alt="Post image" ' +
                          'class="post-image">'
                        : '';
                    
                    var postHtml = 
                        '<article class="post">' +
                            '<header class="post-header">' +
                                '<div class="post-author">' +
                                    '<img src="' + profileImg + '" alt="Avatar" class="author-avatar">' +
                                    '<div class="author-info">' +
                                        '<h3 class="author-name">' + p.nickname + '</h3>' +
                                        '<time class="post-time">' + p.createdAtStr + '</time>' +
                                    '</div>' +
                                '</div>' +
                                '<button class="post-menu">⋮</button>' +
                            '</header>' +
                            '<div class="post-content">' +
                                '<p>' + p.content + '</p>' +
                                imgTag +
                            '</div>' +
                            '<footer class="post-actions">' +
                                '<button class="action-btn' + (p.liked ? ' liked' : '') + '">' +
                                    '👍 ' + p.likeCount +
                                '</button>' +
                                '<button class="action-btn">💬 댓글</button>' +
                                '<button class="action-btn">💾 공유</button>' +
                            '</footer>' +
                        '</article>';

                    $('#feed').append(postHtml);
                    lastId = p.postId;
                });
                loading = false;
            },
            error: function() {
                $('#feed-loading').hide();
                loading = false;
                alert('피드 데이터를 불러오지 못했습니다.');
            }
        });
    }

    $(function() {
        loadFeedChunk();
        $(window).on('scroll', function() {
            if ($(window).scrollTop() + $(window).height() + 100 >= $(document).height()) {
                loadFeedChunk();
            }
        });
    });
</script>
