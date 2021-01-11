<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="title" value="게시물 댓글 수정" />

<%@ include file="../part/head.jspf" %>
	<hr />

	<form action="doModify" method="POST">
		<input type="hidden" name="id" value="${reply.id }" />
		<div>
			번호 : ${reply.id}
		</div>
		<div>
			작성일자 : ${reply.regDate}
		</div>
		<div>
			수정일자 : ${reply.updateDate}
		</div>		
		<div>
			내용 : <input type="text" maxlength="30" placeholder="내용을 입력해주세요."
				name="body" value="${reply.body}" />
		</div>
		<div>
			수정 : <input type="submit" value="수정" />
		</div>
		<div>
			리스트 : <a href="list">리스트</a>
		</div>
	</form>

<%@ include file="../part/foot.jspf" %>