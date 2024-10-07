<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/qnaList.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="memberlist">
	<div class="memberlistlabel">문의</div>
	<!-- 권한부여 -->
	<div class="actiondiv">
		<select class="adminAction">
	        <option value="">선택</option>
	        <option value="grantAdmin">관리권한</option>
	        <option value="revokeAdmin">일반권한</option>
	        <option value="withdrawal">회원탈퇴</option>
	    </select>
	    <button type="button" onclick="AdminAction('${list}')" class="AdminActionB">변경</button>
	</div>
	<!-- 검색 -->
    <div id="searchForm">
        <select id="searchTopic" name="searchTopic" class="searchFormselect">
        	<option value="all">전체</option>
            <option value="title">상품명</option>
            <option value="id">작성자</option>
            <option value="date">작성일</option>
            <option value="status">상태</option>
        </select>
        <input type="text" id="searchKeyword" name="searchKeyword" class="searchForminput">
        <button type="button" onclick="search()" class="searchFormB">검색</button>
    </div>
	<div class="mlist">
		<div class="mtitle">
			<input type='checkbox' name='listcheckbox' value='selectall' onclick='selectAll(this)' class="listcheckbox"/>
			<div class="num">번호</div>
			<div class="title">상품명</div>
			<div class="content">내용</div>
			<div class="id">작성자</div>
			<div class="date">작성일</div>
			<div class="status">상태</div>
		</div>
		<!-- 검색했을 때 결과출력 -->
		<c:choose>
    		<c:when test="${not empty qna_searchList}">
				<c:forEach items="${qna_searchList}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
						<div class="num">${vo.q_num}</div>
						<div class="title">${vo.q_title}</div>
						<div class="content">${vo.q_content}</div>
						<div class="id">${vo.q_id}</div>
						<div class="date">${vo.q_date}</div>
						<div class="status">${vo.q_answerstatus}</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach items="${qnalist}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
  						<div class="num">${vo.q_num}</div>
						<div class="title" onclick="clickname('${vo.q_title}', '${vo.q_id}')">${vo.q_title}</div>
						<div class="content">${vo.q_content}</div>
						<div class="id">${vo.q_id}</div>
						<div class="date">${vo.q_date}</div>
						<div class="status">${vo.q_answerstatus}</div>
					</div>
 				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</body>
<script src="/resources/js/admin/qnaList.js"></script>
</html>