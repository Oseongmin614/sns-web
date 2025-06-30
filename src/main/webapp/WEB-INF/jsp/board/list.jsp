<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판리스트</title>
<script>
	window.onload = function() {
		let addBtn = document.querySelector('#addBtn')
		addBtn.addEventListener('click', function() {
			location.href = "${pageContext.request.contextPath}/board/write"
		})
	}
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my_css.css">
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/jsp/include/topMenu.jsp" />
	</header>
	<section>
		<div align="center">
			<hr>
			<h2>전체게시판</h2>
			<hr>
			<br>
			
			<table style="width: 100%;">
				<tr>
					<th width="7%">번호</th>
					<th>제목</th>
					<th width="15%">작성자</th>
					<th width="17%">등록일</th>
				</tr>
				
				<c:forEach items="${ boardList }" var="board" >
						<tr>
							<td>${ board.no }</td>
							<td>
							
								<a href="${ pageContext.request.contextPath }/board/${ board.no }">
									<c:out value="${ board.title }" />
								</a>
								<%-- 
								<a href="${ pageContext.request.contextPath }/board/detail?no=${ board.no }">
									<c:out value="${ board.title }" />
								</a>
								 --%>
							</td>
							<td>${ board.writer }</td>
							<td>${ board.regDate }</td>
						</tr>
						
				</c:forEach>		
			</table>
			<br>
				<button id="addBtn">새글등록</button>
			<c:if test="${ not empty userVO }">
				<button id="addBtn">새글등록</button>
			</c:if>
		</div>
	</section>
	<footer>
		<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>
	</footer>
</body>
</html>
















