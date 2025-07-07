<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>새 게시글 작성</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/layout.css'/>">
    <style>
        .write-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .write-container h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .write-form .form-group {
            margin-bottom: 20px;
        }
        .write-form label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }
        .write-form input[type="text"],
        .write-form textarea {
            width: calc(100% - 20px);
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        .write-form textarea {
            resize: vertical;
            min-height: 150px;
        }
        .write-form button {
            width: 100%;
            padding: 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .write-form button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
        <jsp:include page="/WEB-INF/jsp/include/sidebar.jsp" />

        <div class="main-content">
            <div class="write-container">
                <h2>새 게시글 작성</h2>
                <form id="postWriteForm" action="<c:url value='/post/write'/>" method="post">
                    <div class="form-group">
                        <label for="title">제목</label>
                        <input type="text" id="title" name="title" required>
                    </div>
                    <div class="form-group">
                        <label for="content">내용</label>
                        <textarea id="content" name="content" required></textarea>
                    </div>
                    <button type="submit">게시글 작성</button>
                </form>
                <div id="errorMessage" class="error-message"></div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
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
                            window.location.href = '<c:url value="/"/>'; // 메인 페이지로 이동
                        } else if (response === 'unauthorized') {
                            alert('로그인이 필요합니다.');
                            window.location.href = '<c:url value="/user/login"/>';
                        } else {
                            $('#errorMessage').text('게시글 작성에 실패했습니다.');
                        }
                    },
                    error: function(xhr, status, error) {
                        if (xhr.status === 401) { // Unauthorized
                            alert('로그인이 필요합니다.');
                            window.location.href = '<c:url value="/user/login"/>';
                        } else {
                            $('#errorMessage').text('서버 오류가 발생했습니다: ' + xhr.responseText);
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>