<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/Header.jsp" %>
<!-- 할인율 적용한 가격 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="/resources/css/user/Payment.css" /> 
<script src="/resources/js/user/Payment.js"></script>
<body>
	<div class="memberlist">
		<div class="mlist">
			<div class="mtitle">
				<div class="pinfo">상품정보</div>
				<div class="price">판매가</div>
				<div class="quantity">수량</div>
				<div class="buyprice">구매가</div>
			</div>
			<c:forEach items="${paymentlist}" var="vo" varStatus="status">
				<div class="mpage" data-order-num="${vo.o_num}" data-product-num="${vo.p_num}">
					<c:choose>
						<c:when test="${empty vo.o_option}">
							<div class="pinfodiv" style="display: flex; justify-content: flex-start;">
								<img class="pinfoimage" src="${vo.o_image}">
								<div class="pnamediv" style="font-family: 'noto';" onclick="clickname(event)">${pnamelist[status.index]}</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="pinfodiv" style="display: flex; justify-content: flex-start;">
								<img class="pinfoimage" src="${vo.o_image}">
								<div class="pnamediv">
									<div style="font-family: 'noto';" onclick="clickname(event)">${pnamelist[status.index]}</div>
									<div class="optiondiv">
										<div>${vo.o_option} ${vo.o_optiondetail}</div>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="price"><fmt:formatNumber value="${vo.o_price}" type="number" maxFractionDigits="0"/>원</div>
					<div class="quantity" style="display: flex; justify-content: center;">
						<div class="buttonDiv">
							<input class="quantityinput" value="${vo.o_quantity}" />
						</div>
					</div>
					<div class="buyprice"><fmt:formatNumber value="${vo.o_price * vo.o_quantity}" type="number" maxFractionDigits="0"/>원</div>
				</div>
			</c:forEach>
		</div>	
		<div class="div1">
			<!-- 쿠폰 -->
			<div class="mlist" style="border-bottom: 1px solid #000;display: flex;align-items: center;width: 50%;margin-bottom: 0;">
				<label class="couponlabel">사용가능한 쿠폰</label>
				<c:choose>
					<c:when test="${not empty availablecouponlist}">
						<select class="couponselect">
					        <c:forEach items="${availablecouponlist}" var="vo" varStatus="status">			        	
			        			<option value="${vo.c_num}" data-coupon-discount="${vo.c_discount}" data-coupon-price="${vo.c_discount_price}"
			        			data-coupon-type="${vo.c_type}" data-coupon-pnum="${vo.p_num}" data-coupon-setting="${vo.c_discount_setting}">${vo.c_name}</option>
					        </c:forEach>
					        <option value="couponnone">쿠폰선택 안함</option>
					    </select>
					</c:when>
				    <c:otherwise>
				    	<input class="couponselect" type="text" value="사용가능한 쿠폰이 없습니다" style="background-color:white" disabled/>
				    </c:otherwise>
				</c:choose>
			    
			</div>	
			<div class="totalpricediv">
		 		<div class="totalpricesubdiv">
		 			<div>결제금액</div>
		 			<div>쿠폰적용</div>
		 			<div class="totalpricetitle">최종 결제금액</div>
		 		</div>
		 		<div class="totalpricesubdiv">
		 			<div class="productprice"><fmt:formatNumber value="${totalprice}" type="number" maxFractionDigits="0"/>원</div>
		 			<div class="couponapply"></div>
		 			<div class="totalprice"></div>
		 		</div>
			</div>
		</div>
		<button class="imgdivbutton" id="shopping">결제하기</button>
	</div>		

</body>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>