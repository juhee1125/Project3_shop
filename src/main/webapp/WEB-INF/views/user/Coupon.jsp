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
<script src="/resources/js/user/Review.js"></script>
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
	        	<label class="paymentlabel">보유 쿠폰</label>	        	
	        	<div class="mtitle" style="border-top: 2px solid; margin-top:40px">
					<div class="couponname">쿠폰명</div>
					<div class="couponbenefit">혜택</div>
					<div class="coupondate">사용기간</div>
				</div>
				<div id="orderContainer">
					<c:forEach var="coupon" items="${couponlist}" varStatus="status">
						<div class="mpage" style="text-align: center;">
							<div style="display: flex; align-items: center; width: 100%;">
								<div class="couponname">
									<div style="color: gray;">${couponpnamelist[status.index]}</div>
									<div>${coupon.c_name}</div>
								</div>
								<c:choose>
									<c:when test="${coupon.c_discount_setting == '정률할인'}">								
										<fmt:formatNumber value="${coupon.c_discount / 100}" pattern="0.##%" var="formattedPercent" />
										<div class="couponbenefit" >${formattedPercent}</div>
									</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${coupon.c_price}" pattern="#,###원" var="formattedPrice" />
										<fmt:formatNumber value="${coupon.c_discount_price}" pattern="#,###원" var="formattedDiscount" />									
										<div class="couponbenefit">${formattedPrice} 구매 시 ${formattedDiscount} 할인</div>
									</c:otherwise>
								</c:choose>
								<div class="coupondate">${coupon.c_discount_start} ~ ${coupon.c_discount_end}</div>
							</div>
						</div>	
					</c:forEach>
				</div>
	        </div>
        </div>
    </div>   
    <!-- 맨위로 버튼 -->
<!--     <a id="btn_gotop" href="#">
        <img src="resources/img/icon/up-arrow-angle.png" id="up">
    </a> -->
</body>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>