<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/delivery.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="memberlist">
	<div class="memberlistlabel">배송정보</div>
	<!-- 권한부여 -->
	<div class="actiondiv">
		<select class="adminAction">
	        <option value="">선택</option>
	        <option value="paymentwaitcount">결제대기</option>
	        <option value="paymentcompletcount">결제완료</option>
	        <option value="preparedeliverycount">배송준비중</option>
	        <option value="deliverycount">배송중</option>
	        <option value="deliverycompletcount">배송완료</option>
	    </select>
	    <button type="button" onclick="AdminAction()" class="AdminActionB">변경</button>
	</div>
	<!-- 검색 -->
    <div id="searchForm">
        <select id="searchTopic" name="searchTopic" class="searchFormselect">
        	<option value="all">전체</option>
            <option value="paymentstatus">결제상태</option>
            <option value="deliverystatus">배송상태</option>
            <option value="name">회원명</option>
            <option value="productname">상품명</option>
        </select>
        <input type="text" id="searchKeyword" name="searchKeyword" class="searchForminput">
        <button type="button" onclick="search()" class="searchFormB">검색</button>
    </div>
	<div class="mlist">
		<div class="mtitle">
			<input type='checkbox' name='listcheckbox' value='selectall' onclick='selectAll(this)' class="listcheckbox"/>
			<div class="num">번호</div>
			<div class="name">회원명</div>
			<div class="productname">상품명</div>
			<div class="quantity">수량</div>
			<div class="paymentstatus">결제상태</div>
			<div class="deliverystatus">베송상태</div>
			<div class="date">주문일</div>
		</div>
		<!-- 검색했을 때 결과출력 -->
		 <c:choose>
    		<c:when test="${not empty order_searchList}">
				<c:forEach items="${order_searchList}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
						<div class="num">${vo.o_num}</div>
						<div class="name">${mnamelist[status.index]}</div>
						<div class="productname">${pnamelist[status.index]}</div>
						<div class="quantity">${vo.o_quantity}</div>
						<div class="paymentstatus">${vo.o_paymentstatus}</div>
						<div class="deliverystatus">${vo.o_deliverystatus}</div>
						<div class="date">${vo.o_date}</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach items="${orderlist}" var="vo" varStatus="status">
					<div class="mpage">
						<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox"/>
 						<div class="num">${vo.o_num}</div>
						<div class="name">${mnamelist[status.index]}</div>
						<div class="productname">${pnamelist[status.index]}</div>
						<div class="quantity">${vo.o_quantity}</div>
						<div class="paymentstatus">${vo.o_paymentstatus}</div>
						<div class="deliverystatus">${vo.o_deliverystatus}</div>
						<div class="date">${vo.o_date}</div>
					</div>
 				</c:forEach>
			</c:otherwise>
		</c:choose> 
	</div>													
</div>
</body>
<script src="/resources/js/admin/delivery.js"></script>
</html>