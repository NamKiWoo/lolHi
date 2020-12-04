<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>
	<h1>게시물 리스트</h1>	
	<hr/>
<c:forEach items="${articles}" var="article">
<!-- 
	<c:set var="detailUrl"
		value="/usr/article/detail?id=${article.id}&listUrl=${encodedCurrentUri}" />
 -->		
	<div>
		번호 :
		${article.id}
		<br /> 작성날짜 : ${article.regDate} <br /> 작성자 :
		 <br /> 갱신날짜 : ${article.updateDate} <br />
		제목 :
		${article.title}
		<br /> 작업 :		
	</div>
	<hr />
</c:forEach>
	
</body>
</html>