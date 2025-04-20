<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/Header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 할인율 적용한 가격 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- 좋아요 표시 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<html>
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
<link rel="stylesheet" type="text/css" href="/resources/css/user/Main.css" /> 
<script src="/resources/js/user/Main.js"></script>
<script src="/resources/js/user/Pagemove.js"></script>
<script src="/resources/js/user/Skin.js"></script>
<body>
<form action="/shop/skin" method="get">
    <div class="wrap">
        <div class="container">
            <img id="img1">
            <button class="shopbutton">Shop</button>
        </div>  
        <div class="container">
	        <c:choose>
		        <c:when test="${loginMember != null}">
			        <label class="recommendtitle">${loginMember.m_name}님을 위한 추천상품</label>
		        </c:when>
	    		<c:otherwise>
			    	<label class="recommendtitle">고객님을 위한 추천상품</label>
	    		</c:otherwise>
	    	</c:choose>
        </div>
        <div class="controller">
			<!-- &lang: 왼쪽 방향 화살표 &rang: 오른쪽 방향 화살표 --> 
			<span class="prev">&lang;</span> 
			<span class="next">&rang;</span>
	    </div>
        <%-- <div id="slideShow">
    		<div class="slides">
		        <c:forEach var="product" items="${productPathMap}" varStatus="status">
		            <div class="product-set" data-product-num="${pnumlist[status.index]}">
	                    <img src="/admin/${product.value}" class="mainimg">
	                    <label class="productlabel">${product.key}</label>
		                <c:choose>
		                    <c:when test="${listdiscount[status.index] != null}">
			                    <label class="discounted-price">
								    <fmt:formatNumber value="${listprice[status.index]}" type="number" groupingUsed="true"/>원
								</label>
		                        <span class="price-container">
			                        <label class="discount">${listdiscount[status.index]}%</label>
			                        <label class="price">
			                        	<fmt:formatNumber value="${listprice[status.index]-(listprice[status.index] * (listdiscount[status.index] / 100))}" type="number" maxFractionDigits="0"/>원
									</label>
								</span>
		                    </c:when>
		                    <c:otherwise>
	                    		<label class="price">${listprice[status.index]}원</label>
		                    </c:otherwise>
		                </c:choose>
		                <span class="icon-container">
		                	<c:choose>
				                <c:when test="${fn:contains(likeloginlist, pnumlist[status.index])}">
				                    <img src="/resources/img/icon/채운찜.png" class="heartimg liked">
				                </c:when>
				                <c:otherwise>
				                    <img src="/resources/img/icon/찜.png" class="heartimg">
				                </c:otherwise>
				            </c:choose>
		                	<img src="/resources/img/icon/장바구니.png" class="cartimg">
		                </span>
		            </div>
		        </c:forEach>
    		</div>
    	</div>
        <div class="container">
	        <label class="recommendtitle">카테고리 랭킹</label>
        </div> --%>
    </div>   
</form>
    <!-- 맨위로 버튼 -->
    <a id="btn_gotop" href="#">
        <img src="resources/img/icon/up-arrow-angle.png" id="up">
    </a>
</body>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>