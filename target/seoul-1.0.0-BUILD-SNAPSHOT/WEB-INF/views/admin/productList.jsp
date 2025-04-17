<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/productList.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="memberlist">
	<div class="memberlistlabel">상품목록</div>
	<!-- 권한부여 -->
	<div class="actiondiv">
		<select class="adminAction">
	        <option value="">선택</option>
	        <option value="productdelete">상품삭제</option>
	    </select>
	    <button type="button" onclick="AdminAction()" class="AdminActionB">변경</button>
	</div>
	<!-- 검색 -->
    <div id="searchForm">
        <select id="searchTopic" name="searchTopic" class="searchFormselect">
        	<option value="all">전체</option>
            <option value="name">상품명</option>
            <option value="price">가격</option>
            <option value="discount">할인율</option>
            <option value="category">카테고리</option>
        </select>
        <input type="text" id="searchKeyword" name="searchKeyword" class="searchForminput">
        <button type="button" onclick="search()" class="searchFormB">검색</button>
    </div>
	<div class="mlist">
		<div class="mtitle">
			<input type='checkbox' name='listcheckbox' value='selectall' onclick='selectAll(this)' class="listcheckbox"/>
			<div class="num">번호</div>
			<div class="name">상품명</div>
			<div class="id">가격</div>
			<div class="pw">수량</div>
			<div class="phone">할인율</div>
			<div class="area1">할인기간</div>
			<div class="email">카테고리</div>
		</div>
		<!-- 검색했을 때 결과출력 -->
		<c:choose>
    		<c:when test="${not empty product_searchList}">
				<c:forEach items="${product_searchList}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
						<div class="num">${vo.p_num}</div>
						<div class="name" onclick="clickname('${vo.p_num}')">${vo.p_name}</div>
						<div class="id">${vo.p_price}</div>
						<div class="pw">${vo.p_quantity}</div>
						<div class="phone">${vo.p_discount}%</div>
						<div class="area1">${vo.p_discount_start}~${vo.p_discount_end}</div>
						<div class="email">${vo.p_category}</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach items="${productlist}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
 						<div class="num">${vo.p_num}</div>
						<div class="name" onclick="clickname('${vo.p_num}')">${vo.p_name}</div>
						<div class="id">${vo.p_price}</div>
						<div class="pw">${vo.p_quantity}</div>
						<div class="phone">${vo.p_discount}%</div>
						<div class="area1">${vo.p_discount_start}~${vo.p_discount_end}</div>
						<div class="email">${vo.p_category}</div>
					</div>
 				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>													
</div>
</body>
<script src="/resources/js/admin/productList.js"></script>
</html>