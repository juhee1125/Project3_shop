<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 할인율 적용한 가격 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- 좋아요 표시 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/Header.jsp" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="/resources/css/user/My.css" /> 
<body>
    <div class="wrap">
    	<ul>
   			<li><a href="/my/my">마이페이지</a></li>
			<li><a href="/delivery/inquiry">주문/배송 조회</a></li>
			<li><a href="/coupon/coupon">쿠폰</a></li>
			<li><a href="/qna/qnainquiry">문의</a></li>
			<li><a href="/review/review">리뷰</a></li>
			<li><a href="/refund/refund">환불</a></li>
		</ul>
        <div class="frame">
        	<div style="width: 80%;">
	        	<label class="paymentlabel">주문/배송 조회</label>
	        	<div class="div1">
		        	<div class="paymentdiv">
		        		<label class="paymentwaitcount">${paymentwaitcount}</label>
		        		<label class="payment">결제대기</label>
		        	</div>
		        	<img src="/resources/img/icon/오른쪽.png" class="paymentimg"/>
		        	<div class="paymentdiv">
		        		<label class="paymentwaitcount">${paymentcompletcount}</label>
		        		<label class="payment">결제완료</label>
		        	</div>
		        	<img src="/resources/img/icon/오른쪽.png" class="paymentimg"/>
		        	<div class="paymentdiv">
		        		<label class="paymentwaitcount">${preparedeliverycount}</label>
		        		<label class="payment">배송준비중</label>
		        	</div>
		        	<img src="/resources/img/icon/오른쪽.png" class="paymentimg"/>
		        	<div class="paymentdiv">
		        		<label class="paymentwaitcount">${deliverycount}</label>
		        		<label class="payment">배송중</label>
		        	</div>
		        	<img src="/resources/img/icon/오른쪽.png" class="paymentimg"/>
		        	<div class="paymentdiv">
		        		<label class="paymentwaitcount">${deliverycompletcount}</label>
		        		<label class="payment">배송완료</label>
		        	</div>
	        	</div>
	        	<div class="div2">
	        		<label style="font-family: 'noto'; margin-right: 15px;">구매기간</label>
	        		<div>
		        		<div style="margin-bottom: 16px;">
				        	<button class="dateinquirybutton">1개월</button>
			        		<button class="dateinquirybutton">3개월</button>
			        		<button class="dateinquirybutton">6개월</button>
			        		<button class="dateinquirybutton">12개월</button>
		        		</div>
		        		<input type="date" id="dateinput" name="dateinputstart" required>
		        		<label style="padding: 0px 15px;">~</label>
		        		<input type="date" id="dateinput" name="dateinputend" required>	
		        		<button class="inquirybutton" >조회</button>
		        	</div>
	        	</div>
	        	<div class="mtitle">
					<div class="odatetitle">주문일</div>
					<div class="mtitlediv">
						<div class="odproducttitle">상품정보</div>
						<div class="oquantitytitle">수량</div>
						<div class="opricetitle">총결제금액</div>
						<div class="ostatustitle">배송정보</div>
					</div>
				</div>
				<div id="orderContainer"></div>
	        </div>
        </div>
    </div>   
    <!-- 맨위로 버튼 -->
<!--     <a id="btn_gotop" href="#">
        <img src="resources/img/icon/up-arrow-angle.png" id="up">
    </a> -->
</body>
<script src="/resources/js/user/Delivery.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>