<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/memberList.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="memberlist">
	<div class="memberlistlabel">회원정보</div>
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
            <option value="id">아이디</option>
            <option value="name">이름</option>
            <option value="area">주소</option>
        </select>
        <input type="text" id="searchKeyword" name="searchKeyword" class="searchForminput">
        <button type="button" onclick="search()" class="searchFormB">검색</button>
    </div>
	<div class="mlist">
		<div class="mtitle">
			<input type='checkbox' name='listcheckbox' value='selectall' onclick='selectAll(this)' class="listcheckbox"/>
			<div class="num">번호</div>
			<div class="name">이름</div>
			<div class="id">아이디</div>
			<div class="pw">비밀번호</div>
			<div class="phone">핸드폰번호</div>
			<div class="area1">우편번호</div>
			<div class="area2">주소</div>
			<div class="area3">상세주소</div>
			<div class="email">이메일</div>
			<div class="role">역할</div>
			<div class="rating">등급</div>
		</div>
		<!-- 검색했을 때 결과출력 -->
		<c:choose>
    		<c:when test="${not empty user_searchList}">
				<c:forEach items="${user_searchList}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
						<div class="num">${vo.m_num}</div>
						<div class="name">${vo.m_name}</div>
						<div class="id">${vo.m_id}</div>
						<div class="pw">${vo.m_pw}</div>
						<div class="phone">${vo.m_phone}</div>
						<div class="area1">${vo.m_postalcode}</div>
						<div class="area2">${vo.m_adress}</div>
						<div class="area3">${vo.m_detailAddress}</div>
						<div class="email">${vo.m_email}</div>
						<div class="role">${roles[status.index]}</div> 
						<div class="rating">${vo.m_Rating}</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
 						<div class="num">${vo.m_num}</div>
						<div class="name">${vo.m_name}</div>
						<div class="id">${vo.m_id}</div>
						<div class="pw">${vo.m_pw}</div>
						<div class="phone">${vo.m_phone}</div>
						<div class="area1">${vo.m_postalcode}</div>
						<div class="area2">${vo.m_adress}</div>
						<div class="area3">${vo.m_detailAddress}</div>
						<div class="email">${vo.m_email}</div>
						<div class="role">${roles[status.index]}</div> 
						<div class="rating">${vo.m_Rating}</div>
					</div>
 				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</body>
<script src="/resources/js/admin/memberList.js"></script>
</html>