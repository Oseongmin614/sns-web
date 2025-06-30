<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#content > * {
		width: 80%;
	}
	
	.error {
		font-size: 0.7em;
		color: red;
	}
</style>
<link rel="stylesheet" href="${ pageContext.request.contextPath }/resources/css/layout.css">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/resources/css/my_css.css">
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/jsp/include/topMenu.jsp" />
	</header>
	<section>
		<div align="center" id="content">
			<hr>
			<h2>새글등록</h2>
			<hr>
			<br>
			<form:form method="post" modelAttribute="boardVO">
				<table style="width: 100%;">
					<tr>
						<th width="25%">제목</th>
						<td>
							<form:input path="title"/> <form:errors path="title" class="error"/>
						</td>
					</tr>
					<tr>
						<th width="25%">작성자</th>
						<td>
							<form:input path="writer"/> <form:errors path="writer" class="error"/>
						</td>
					</tr>
					<tr>
						<th width="25%">내용</th>
						<td>
							<form:textarea path="content" rows="7" cols="80"/>
							<form:errors path="content" class="error" />
						</td>
					</tr>
				</table>
				<br>
				<form:button type="submit">등록</form:button>
			</form:form>
		</div>
	</section>
	<footer>
		<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>
	</footer>
</body>
</html>




















