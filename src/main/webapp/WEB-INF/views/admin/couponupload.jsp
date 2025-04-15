<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/couponupload.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<script src="/resources/js/admin/couponupload.js"></script>
<body>
<div class="memberlist">
    <div class="productuploadlabel">쿠폰등록</div>
    <div class="frame">
        <div style="margin-bottom: 21em;">
        	<div class="coupondiv">
        		<div class="couponlabeldiv">
	            	<label class="couponname">쿠폰명</label>
	            </div>
	            <input type="text" class="nameinput" id="nameinput" name="nameinput" required>
	        </div>
	        <div class="coupondiv">
	        	<div class="couponlabeldiv">
	        		<label class="discountpercent">발행유형</label>
	        	</div>
	        	<input type="radio" name="coupontargetradio" class="couponradio" value="product" checked="checked">
	        	<label class="couponlabel">상품</label>
	        	<input type="radio" name="coupontargetradio" class="couponradio" value="customer" >
	        	<label>고객</label>
	        </div>
	        <div class="coupondiv">
	        	<div class="couponlabeldiv"></div>
	        	<select class="nameinput" id="productselect">
	        		<c:forEach items="${productlist}" var="vo" varStatus="status">
	        			<option value="${vo.p_name}">${vo.p_name}</option>
	        		</c:forEach>
				</select>
	        </div>
	        <div class="coupondiv">
	        	<div class="couponlabeldiv">
	        		<label class="discountpercent">발행건수</label>
	        	</div>
	        	<input type="text" class="numberinput" id="numberinput" name="numberinput">	
	        	<input type="checkbox" name="couponnumbercheck" style="margin-right: 10px" class="numbercheck">
	        	<label>제한없음</label>    
	        </div>
	        <div class="coupondiv">
	        	<div class="couponlabeldiv">
	        		<label class="discountpercent">할인설정</label>
	        	</div>
	        	<input type="radio" name="coupontyperadio" class="couponradio" value="percentradio" checked="checked">
	        	<label class="couponlabel">정률할인</label>
	        	<input type="radio" name="coupontyperadio" class="couponradio" value="priceradio">
	        	<label>정액할인</label>
	        </div>
	        <div class="coupondiv">
	        	<div class="couponlabeldiv"></div>
	            <input type="text" class="percentinput" id="percentinput" name="percentinput" placeholder="할인율">
	            <label class="percent">%</label>
	            <input type="text" class="percentinput" id="priceinput" name="percentinput" placeholder="주문금액" disabled>
	            <label style="margin-right: 10px;">원 이상 구매 시,</label>
	            <input type="text" class="percentinput" id="priceinput" name="percentinput" placeholder="할인금액" disabled>
	            <label class="">원 할인</label>
	        </div>
	        <div class="coupondiv">
	        	<div class="couponlabeldiv">
	            	<label class="discountdate">할인기간</label>
	            </div>
	            <input type="date" id="dateinputstart" name="dateinputstart" required>
	            <label class="from">~</label>
	            <input type="date" id="dateinputend" name="dateinputend" required>
	        </div>
        </div>
    </div>
    <button class="productbutton">등록</button>
</div>
</body>
</html>