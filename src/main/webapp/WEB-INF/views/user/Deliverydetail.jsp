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
	        	<label class="paymentlabel">상품 세부내역</label>
	        	<div class="div2" style="align-items: center;">
	        		<div style="font-family: noto;">주문일</div>
	        		<div style="padding: 0 10px;">${orderDate}</div>
	        		<div style="font-family: noto;">상품번호</div>
	        		<div style="padding: 0 10px;">${orderNumber}</div>
	        		<c:choose>
						<c:when test="${orderDstatus == '배송완료'}">
							<button class="reviewbutton" onclick="Returnpopupopen()">반품신청</button>
						</c:when>
						<c:otherwise>
							<button class="reviewbutton" onclick="Cancelpopupopen()">결제취소</button>
						</c:otherwise>
					</c:choose>
	        	</div>
	        	<div class="mtitle">
					<div class="odproducttitle">상품정보</div>
					<div class="oquantitytitle">수량</div>
					<div class="opricetitle">총결제금액</div>
					<div class="ostatustitle">배송정보</div>
				</div>
				<div id="orderContainer">
					<c:forEach var="order" items="${orderlist}" varStatus="status">
						<div class="mpage" data-product-num="${order.p_num}">
							<div style="display: flex; align-items: center; width: 100%;">
								<img src="${order.o_image}" class="oimage"/>
								<c:choose>
									<c:when test="${not empty order.o_option}">
										<div style="display: flex; flex-direction: column;">
											<label class="productnamelabel" id="name-${status.index}">${productnamelist[status.index]}</label>
											<div>
												<label class="productoptionlabel" id="option-${status.index}">${order.o_option}</label>
												<label class="productdetaillabel" id="detail-${status.index}">${order.o_optiondetail}</label>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div>
											<label class="productnamelabel" id="name-${status.index}">${productnamelist[status.index]}</label>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="oquantity">${order.o_quantity}</div>
							<fmt:formatNumber value="${order.o_price * order.o_quantity}" pattern="#,###원" var="formattedPrice" />
							<div class="oprice">${formattedPrice}</div>
							<div class="odeliverystatus">
								<div>${order.o_paymentstatus}</div>
							</div>
						</div>
					</c:forEach>
	        </div>
	        <div class="totalpricediv">
	        	<div class="totalpricesubdiv">
			 		<div class="pricedivtitle">주문금액</div>
			 		<div class="pricesubdiv">
			 			<fmt:formatNumber value="${totalprice + o_shippingfee}" pattern="#,###원" var="deliveryPrice" />
			 			${deliveryPrice}
			 		</div>
			 		<div class="pricedetaildiv">
				 		<div>상품금액</div>
				 		<div>
				 			<fmt:formatNumber value="${totalprice}" pattern="#,###원" var="orderPrice" />
				 			${orderPrice}
				 		</div>
				 	</div>
			 		<div class="pricedetaildiv">
				 		<div>배송비</div>
				 		<div>
				 			<fmt:formatNumber value="${o_shippingfee}" pattern="#,###원" var="shippingPrice" />
				 			${shippingPrice}
				 		</div>
			 		</div>
			 	</div>
			 	<div class="totalpricesubdiv">
		 			<div class="pricedivtitle">쿠폰할인금액</div>
			 			<c:choose>
			 				<c:when test="${not empty coupon}">
			 					<c:choose>
									<c:when test="${coupon.c_discount_setting == '정률할인'}">
										<div class="pricesubdiv" id="${customerpname}">
											<fmt:formatNumber value="${couponuse.cu_apply_price}" pattern="-#,##0원" var="couponapply" />
											${couponapply}
										</div>
							 			<div class="pricedetaildiv">
							 				<div >${coupon.c_name}</div>
							 				<fmt:formatNumber value="${coupon.c_discount}" pattern="-#'%'" var="couponPersent" />
											<div>${couponPersent}</div>
										</div>
										<div class="pricedetaildiv"></div>		
									</c:when>
									<c:otherwise>
										<div class="pricesubdiv" id="${customerpname}">
											<fmt:formatNumber value="${couponuse.cu_apply_price}" pattern="-#,##0원" var="couponapply" />
											${couponapply}
										</div>
										<div class="pricedetaildiv">
							 				<div>${coupon.c_name}</div>
											<div>${couponPrice}</div>
										</div>			
										<div class="pricedetaildiv"></div>						
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<div  class="pricesubdiv">0원</div>
							</c:otherwise>
						</c:choose>
					</div>
		 		</div>
		 		<div class="totalpricediv2">
		 			<div>총 결제금액</div>
 			 		<c:choose>
			 			<c:when test="${not empty coupon}">
 				 			<fmt:formatNumber value="${(totalprice + o_shippingfee) - couponuse.cu_apply_price}" pattern="#,###원" var="totalprice" />
			 				<div>${totalprice} </div>
					 	</c:when>
					 	<c:otherwise>
					 		<fmt:formatNumber value="${(totalprice + o_shippingfee)}" pattern="#,###원" var="totalprice" />
			 				<div>${totalprice} </div>
					 	</c:otherwise>
			 		</c:choose>
			 	</div>
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