<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/productupdate.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<script src="/resources/js/admin/productupdate.js"></script>
<body>
<div class="memberlist">
	<div class="productuploadlabel">상품수정</div>
	<div class="frame">
		<div style="margin-bottom: 21em;">
			<label class="productinformation">상품정보</label>
			<div class="line1"></div>
	 		<label class="productname">상품명*</label>
			<input type="text" class="nameinput" value="${productupdatelist.p_name}"></input>
			<label class="productprice">가격*</label>
			<input type="text" class="priceinput" value="${productupdatelist.p_price}"></input>
			<label class="productquantity">전체수량*</label>
			<input type="number" class="quantityinput" min="0" max="999" value="${productupdatelist.p_quantity}"></input>
			<label class="productdiscount">할인</label>
			<div class="line2"></div>
			<label class="discountpercent">할인율</label>
			<input type="text" class="percentinput" value="${productupdatelist.p_discount}"></input>
			<label class="percent">%</label>
			<label class="discountdate">할인기간</label>
			<input type="date"id="dateinputstart" data-placeholder="시작일" required aria-required="true" value="${productupdatelist.p_discount_start}"></input>
			<label class="from">~</label>
			<input type="date" id="dateinputend" data-placeholder="종료일" required aria-required="true" value="${productupdatelist.p_discount_end}"></input>
		</div>
		<c:set var="selectedCategory" value="${productupdatelist.p_category}" />
		<label class="category">카테고리*</label>
		<div class="category-group">
		    <input type="radio" class="categoryradio" name="categoryradio" value="skin" <c:if test="${selectedCategory == 'skin'}">checked</c:if>>
		    <label class="categorylabel">스킨케어</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="sun" <c:if test="${selectedCategory == 'sun'}">checked</c:if>>
		    <label class="categorylabel">선케어</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="base" <c:if test="${selectedCategory == 'base'}">checked</c:if>>
		    <label class="categorylabel">베이스메이크업</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="eye" <c:if test="${selectedCategory == 'eye'}">checked</c:if>>
		    <label class="categorylabel">아이메이크업</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="lip" <c:if test="${selectedCategory == 'lip'}">checked</c:if>>
		    <label class="categorylabel">립메이크업</label>
		</div>
	</div>
	<button class="productbutton">수정</button>
</div>
</body>
<script src="/resources/js/admin/memberList.js"></script>
</html>