<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    

<c:set var="title" value="${board.name } 게시물 리스트" />

<%@ include file="../part/head.jspf" %>
	<hr/>
	<div>총 게시물 수 : ${totalCount}</div>
	
	<div>
		<form>
			<div>
				<input type="text" name="searchKeyword" placeholder="검색어" value=${param.searchKeyword}>
			</div>
			<div>
				<input type="submit" value="검색">
			</div>
		</form>
	</div>	
	
<c:forEach items="${articles}" var="article">
	
	<c:set var="detailUrl" value="/usr/article-${board.code}/detail?id=${article.id}&listUrl=${encodedCurrentUri}" />
		
	<div>
		번호 : <a href="${detailUrl}">${article.id}</a>
		<br /> 
		작성날짜 : ${article.regDate} 
		<br /> 
		작성자 : ${article.extra.writer}
		<br />		
		갱신날짜 : ${article.updateDate} 
		<br />
		<!-- 제목 : <a href="detail?id=${article.id}">${article.title}</a>    -->
		제목 : <a href="${detailUrl}">${article.title}</a>
		<br /> 
		<c:if test="${article.extra.actorCanDelete}">
		작업 : <a onclick="if(confirm('삭제하시겠습니까?') == false) return false;" href="doDelete?id=${article.id}">삭제</a>
		</c:if>
		<c:if test="${article.extra.actorCanModify}">
			   <a href="modify?id=${article.id}">수정</a>
		</c:if>
	</div>
	<hr />
</c:forEach>

	<div>
		<a href="write">글쓰기</a>
	</div>
	
	<style>
		.selected {
			color:red;
		}
	</style>
	<h2>페이지</h2>
	<div>
		<!-- 첫 페이지로 이동버튼이 노출될 필요가 있는지 여부 -->
		<c:set var="goFirstBtnNeedToShow" value="${page > pageMenuArmSize +1}" />
		<!-- 마지막 페이지로 이동버튼이 노출될 필요가 있는지 여부 -->
		<c:set var="goLastBtnNeedToShow" value="true" />
		
		<c:if test="${0 == totalPage}">
			<c:set var="goFirstBtnNeedToShow" value="false" />
		</c:if>
		
		<!-- 첫 페이지로 이동버튼이 노출될 필요가 있다면 노출 -->
		<c:if test="${goFirstBtnNeedToShow}">
			<a href="?page=1&searchKeyword=${param.searchKeyword}">◀</a>
		</c:if>
		<c:forEach var="i" begin="${pageMenuStart}" end="${pageMenuEnd}">
			<c:set var="className" value="${i == page ? 'selected' : '' }" />
			<a class="${className}" href="?page=${i}&searchKeyword=${param.searchKeyword}">${i}</a>
			
			<c:if test="${i == totalPage}">
				<c:set var="goLastBtnNeedToShow" value="false" />
			</c:if>
		</c:forEach>

		<c:if test="${0 == totalPage}">
			<c:set var="goLastBtnNeedToShow" value="false" />
		</c:if>

	<!-- 마지막 페이지로 이동버튼이 노출될 필요가 있다면 노출 -->
		<c:if test="${goLastBtnNeedToShow}">
			<a href="?page=${totalPage}&searchKeyword=${param.searchKeyword}">▶</a>
		</c:if>
	</div>
		
<%@ include file="../part/foot.jspf" %>