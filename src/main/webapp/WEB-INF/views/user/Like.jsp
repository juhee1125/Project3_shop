c<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<link rel="stylesheet" type="text/css" href="/resources/css/user/Like.css" /> 
<script src="/resources/js/user/Main.js"></script>
<script src="/resources/js/user/Pagemove.js"></script>
<body>
    <div class="wrap">
    	<select id="job" name="job" size="1">
			<option value="all">전체</option>
			<option value="skin">스킨케어</option>
			<option value="sun">선케어</option>
			<option value="base">베이스메이크업</option>
			<option value="eye">아이메이크업</option>
			<option value="lip">립메이크업</option>
		</select>
        <div class="frame">
        	<c:choose>
        		<c:when test="${not empty like_selectList}">
			        <c:forEach var="productselect" items="${like_selectList}" varStatus="status">
			            <div class="product-set">
			                <div class="pathdiv">
			                    <img src="/admin/${like_categorypathList[status.index]}" class="mainimg">
			                </div>
			                <div class="namediv">
			                    <label class="productlabel">${productselect.p_name}</label>
			                </div>
			                <div>
				                <c:choose>
				                    <c:when test="${productselect.p_discount != null}">
				                        <label class="discounted-price">${productselect.p_price}원</label>
				                        <div class="discountdiv">
					                        <label class="discount">${productselect.p_discount}%</label>
					                        <label class="price">
					                        	<fmt:formatNumber value="${productselect.p_price-(productselect.p_price * (productselect.p_discount / 100))}" type="number" maxFractionDigits="0"/>원
											</label>
				                        </div>
				                    </c:when>
				                    <c:otherwise>
				                    	<div class="pricediv">
				                    		<label class="price">${productselect.p_price}원</label>
				                    	</div>
				                    </c:otherwise>
				                </c:choose>
			                </div>
			                <div class="imgdiv">
			                    <img src="/resources/img/icon/채운찜.png" class="heartimg liked">
			                	<img src="/resources/img/icon/장바구니.png" class="cartimg">
			                </div>
			            </div>
			        </c:forEach> 
		        </c:when>
		        <c:otherwise>
		        	<c:forEach var="product" items="${productlist}" varStatus="status">
			            <div class="product-set">
			                <div class="pathdiv">
			                    <img src="/admin/${likepathlist[status.index]}" class="mainimg">
			                </div>
			                <div class="namediv">
			                    <label class="productlabel">${product.p_name}</label>
			                </div>
			                <div>
				                <c:choose>
				                    <c:when test="${product.p_discount != null}">
				                        <label class="discounted-price">${product.p_price}원</label>
				                        <div class="discountdiv">
					                        <label class="discount">${product.p_discount}%</label>
					                        <label class="price">
					                        	<fmt:formatNumber value="${product.p_price-(product.p_price * (product.p_discount / 100))}" type="number" maxFractionDigits="0"/>원
											</label>
				                        </div>
				                    </c:when>
				                    <c:otherwise>
				                    	<div class="pricediv">
				                    		<label class="price">${product.p_price}원</label>
				                    	</div>
				                    </c:otherwise>
				                </c:choose>
			                </div>
			                <div class="imgdiv">
			                    <img src="/resources/img/icon/채운찜.png" class="heartimg liked">
			                	<img src="/resources/img/icon/장바구니.png" class="cartimg">
			                </div>
			            </div>
			        </c:forEach> 
		        </c:otherwise>
	        </c:choose>
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