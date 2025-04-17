<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/couponList.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="memberlist">
	<div class="memberlistlabel">쿠폰</div>
	<!-- 권한부여 -->
	<div class="actiondiv">
		<select class="adminAction">
	        <option value="">선택</option>
	        <option value="coupondelete">쿠폰삭제</option>
	    </select>
	    <button type="button" onclick="AdminAction()" class="AdminActionB">변경</button>
	</div>
	<!-- 검색 -->
    <div id="searchForm">
        <select id="searchTopic" name="searchTopic" class="searchFormselect">
        	<option value="all">전체</option>
            <option value="name">쿠폰명</option>
            <option value="type">발행유형</option>
        </select>
        <input type="text" id="searchKeyword" name="searchKeyword" class="searchForminput">
        <button type="button" onclick="search()" class="searchFormB">검색</button>
    </div>
	<div class="mlist">
		<div class="mtitle">
			<input type='checkbox' name='listcheckbox' value='selectall' onclick='selectAll(this)' class="listcheckbox"/>
			<div class="num">번호</div>
			<div class="name">쿠폰명</div>
			<div class="type">발행유형</div>
			<div class="period">사용기간</div>
			<div class="date">등록일</div>
		</div>
		<!-- 검색했을 때 결과출력 -->
		<c:choose>
    		<c:when test="${not empty coupon_searchList}">
				<c:forEach items="${coupon_searchList}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
						<div class="num">${vo.c_num}</div>
						<div class="name">${vo.c_name}</div>
						<div class="type">${vo.c_type}</div>
						<div class="period">${vo.c_discount_start} ~ ${vo.c_discount_end}</div>
						<div class="date">${vo.c_date}</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach items="${couponlist}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
 						<div class="num">${vo.c_num}</div>
						<div class="name">${vo.c_name}</div>
						<div class="type">${vo.c_type}</div>
						<div class="period">${vo.c_discount_start} ~ ${vo.c_discount_end}</div>
						<div class="date">${vo.c_date}</div>
					</div>
 				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</body>
<script src="/resources/js/admin/couponList.js"></script>
</html>