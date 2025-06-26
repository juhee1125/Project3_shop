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
<script src="/resources/js/user/Pagemove.js"></script>
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
	        </div>
	        <div style="width: 80%;margin-top: 30px;display: flex;">
	        	<div class="div2group">
		        	<div class="div2_my">
		        		<label class="paymentlabel">찜한 상품</label>
		        		<a href="/like/like" id="plusatag">더보기</a>
		        	</div>	        	
		        	<div style="display: flex;margin-top: 20px;">
		        		<c:forEach var="path" items="${pppathlist}" varStatus="status">
		        			<div class="product-set">
		        				<img src="/admin/${path}" class="pathimage" onclick="window.location.href='/detail/detail?num=${pnumlist[status.index]}';"/>
		        				<label class="productlabel" onclick="window.location.href='/detail/detail?num=${pnumlist[status.index]}';">${pnamelist[status.index]}</label>
		        				<div style="width: 90%;">
					                <c:choose>
					                    <c:when test="${pdiscountlist[status.index] != null}">
					                        <label class="discounted-price">
					                        	<fmt:formatNumber value="${ppricelist[status.index]}" type="number" maxFractionDigits="0"/>원
					                        </label>
					                        <div class="discountdiv">
						                        <label class="discount">${pdiscountlist[status.index]}%</label>
						                        <label class="price">
						                        	<fmt:formatNumber value="${ppricelist[status.index]-(ppricelist[status.index] * (pdiscountlist[status.index] / 100))}" type="number" maxFractionDigits="0"/>원
												</label>
					                        </div>
					                    </c:when>
					                    <c:otherwise>
					                    	<div class="pricediv">
					                    		<label class="price">
					                    			<fmt:formatNumber value="${ppricelist[status.index]}" type="number" maxFractionDigits="0"/>원
					                    		</label>
					                    	</div>
					                    </c:otherwise>
					                </c:choose>
				                </div>
		        			</div>
		        		</c:forEach>
	        		</div>		        	
	        	</div>
	        	<div style="width: 50%;margin-left: 10px;">
		        	<div class="div2_my">
			        	<label class="paymentlabel">상품 문의내역</label>
			        	<a href="/shop/skin" id="plusatag">더보기</a>
			        </div>
			        <div class="contentdiv">
		        		<c:forEach var="qcontent" items="${qcontentlist}" varStatus="status">
				        	<div class="qnalistdiv">
					        	<c:choose>
									<c:when test="${qstatuslist[status.index] == '답변완료'}">
										<label class="qnaq_content">${qcontent}</label>
										<label class="qnastatus_answered">${qstatuslist[status.index]}</label>
									</c:when>
									<c:otherwise>
										<label class="qnaq_content">${qcontent}</label>
										<label class="qnastatus">${qstatuslist[status.index]}</label>
									</c:otherwise>
								</c:choose>
				        	</div>
			        	</c:forEach>
		        	</div>
	        	</div>
	        </div>
        </div>
    </div>   
    <!-- 맨위로 버튼 -->
<!--     <a id="btn_gotop" href="#">
        <img src="resources/img/icon/up-arrow-angle.png" id="up">
    </a> -->
</body>
<script src="/resources/js/user/Like.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>