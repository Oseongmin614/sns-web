<%@page import="kr.ac.kopo.board.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#content > * {
		width: 80%;
	}
	td {
		padding-left: 10px;
	}
</style>
<script>
	window.onload = function() {
		let listBtn = document.querySelector('#listBtn')
		listBtn.addEventListener('click', function() {
			location.href = 'list.jsp'
		})
	}
</script>
</head>
<body>
	<div align="center" id="content">
		<hr>
		<h2>상세게시글</h2>
		<hr>
		<table border="1" >
			<tr>
				<th width="27%">번호</th>
				<td>${ board.no }</td>
			</tr>
			<tr>
				<th width="27%">제목</th>
				<td><c:out value="${ board.title }" /></td>
			</tr>
			<tr>
				<th width="27%">작성자</th>
				<td>${ board.writer }</td>
			</tr>
			<tr>
				<th width="27%">내용</th>
				<td><c:out value="${ board.content }" /></td>
			</tr>
			<tr>
				<th width="27%">조회수</th>
				<td>${ board.viewCnt }</td>
			</tr>
			<tr>
				<th width="27%">등록일</th>
				<td>${ board.regDate }</td>
			</tr>
		</table>
		<br>
		<button id="listBtn">목록</button>
		<button id="">수정</button>
		<button id="">삭제</button>
	</div>
</body>
</html>





