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
		</ul>
        <div class="frame">
        	<div style="width: 80%;">
	        	<label class="paymentlabel">리뷰</label>	        	
	        	<div class="mtitle" style="border-top: 2px solid; margin-top:40px">
					<div class="odatetitle">주문일</div>
					<div class="odproducttitle">상품정보</div>
					<div class="qanswerstatustitle">리뷰작성</div>
				</div>
				<div id="orderContainer">
					<c:forEach var="order" items="${orderlist}" varStatus="status">
						<div class="mpage">
							<label class="odate">
							    <c:set var="formattedDate">
							        <fmt:formatDate value="${order.o_date}" pattern="yyyy.MM.dd" />
							    </c:set>
							    ${fn:replace(formattedDate, '.', '.')}
							</label>

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
							<div class="reviewBdiv">
								<button class="reviewbutton" onclick="Reviewpopupopen(${status.index}, ${order.o_num})">리뷰작성</button>
							</div>
							<div class="Reviewpopup" ></div>
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