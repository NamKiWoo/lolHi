<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="title" value="${board.name} 게시물 작성" />

<%@ include file="../part/head.jspf" %>

	<form action="doWrite" method="POST">
		<div>
			제목 : <input type="text" maxlength="30" placeholder="제목을 입력해주세요."
				name="title" />
		</div>
		<div>
			내용 : <input type="text" maxlength="30" placeholder="내용을 입력해주세요."
				name="body" />
		</div>
		<div>
			제출 : <input type="submit" value="작성" />
		</div>
	</form>
	<div>
		리스트 : <a href="list">리스트</a>
	</div>

<%@ include file="../part/foot.jspf" %>