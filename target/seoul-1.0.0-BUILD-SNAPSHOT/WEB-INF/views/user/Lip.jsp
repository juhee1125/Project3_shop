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
<link rel="stylesheet" type="text/css" href="/resources/css/user/Skin.css" /> 
<script src="/resources/js/user/Skin.js"></script>
<script src="/resources/js/user/Pagemove.js"></script>
<body>
    <div class="wrap">
        <div class="container">
            <img src="/resources/img/립상단이미지.jpg" id="img1">
        </div>
        <div class="frame">
	        <c:forEach var="product" items="${lipcategorylist}" varStatus="status">
	            <div class="product-set" data-product-num="${product.p_num}">
	                <div class="pathdiv">
	                    <img src="/admin/${lippathlist[status.index]}" class="mainimg">
	                </div>
	                <div class="namediv">
	                    <label class="productlabel">${product.p_name}</label>
	                </div>
	                <div>
		                <c:choose>
		                    <c:when test="${product.p_discount != null}">
		                        <label class="discounted-price">
		                        	<fmt:formatNumber value="${product.p_price}" type="number" maxFractionDigits="0"/>원
		                        </label>
		                        <div class="discountdiv">
			                        <label class="discount">${product.p_discount}%</label>
			                        <label class="price">
			                        	<fmt:formatNumber value="${product.p_price-(product.p_price * (product.p_discount / 100))}" type="number" maxFractionDigits="0"/>원
									</label>
		                        </div>
		                    </c:when>
		                    <c:otherwise>
		                    	<div class="pricediv">
		                    		<label class="price">
		                    			<fmt:formatNumber value="${product.p_price}" type="number" maxFractionDigits="0"/>원
		                    		</label>
		                    	</div>
		                    </c:otherwise>
		                </c:choose>
	                </div>
	                <div class="imgdiv">
	                	<c:choose>
			                <c:when test="${fn:contains(likeproductlist, product.p_name)}">
			                    <img src="/resources/img/icon/채운찜.png" class="heartimg liked">
			                </c:when>
			                <c:otherwise>
			                    <img src="/resources/img/icon/찜.png" class="heartimg">
			                </c:otherwise>
			            </c:choose>
	                	<img src="/resources/img/icon/장바구니.png" class="cartimg">
	                </div>
	            </div>
	        </c:forEach>
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