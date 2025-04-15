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
<link rel="stylesheet" type="text/css" href="/resources/css/user/Shopping.css" /> 
<script src="/resources/js/user/Shopping.js"></script>
<body>
	<div class="memberlist">
		<!-- 권한부여 -->
		<div class="actiondiv">
			<select class="adminAction">
		        <option value="">선택</option>
		        <option value="productdelete">상품삭제</option>
		    </select>
		    <button type="button" onclick="AdminAction()" class="AdminActionB">변경</button>
		</div>
		<div class="mlist">
			<div class="mtitle">
				<input type='checkbox' name='listcheckbox' value='selectall' onclick='selectAll(this)' class="listcheckbox" checked/>
				<div class="pinfo">상품정보</div>
				<div class="price">판매가</div>
				<div class="quantity">수량</div>
				<div class="buyprice">구매가</div>
				<div class="shippingfee">배송정보</div>
			</div>
			<c:forEach items="${orderlist}" var="vo" varStatus="status">
				<div class="mpage" data-order-num="${vo.o_num}">
					<input type='checkbox' name='listcheckbox' value='selecteach' class="listcheckbox" id="checkbox" checked/>
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
										<div class="optionchange" onclick="optionchange(event)">옵션변경</div>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="price"><fmt:formatNumber value="${vo.o_price}" type="number" maxFractionDigits="0"/>원</div>
					<div class="quantity" style="display: flex; justify-content: center;">
						<div class="buttonDiv">
							<button class="minusBtn"></button>
							<input class="quantityinput" value="${vo.o_quantity}"></input>
							<button class="plusBtn"></button>
						</div>
					</div>
					<div class="buyprice"></div>
					<div class="shippingfee"><fmt:formatNumber value="${o_shippingfee}" type="number" maxFractionDigits="0"/>원</div>
				</div>
			</c:forEach>
		</div>	
		<div class="priceplusdiv">
			<div style="width: 150px;">
				<div class="ordertotalpricetitle">총 구매금액</div>
				<div class="ordertotalprice"></div>
			</div>
			<img class="plusimg" src="/resources/img/icon/플러스.png"/>
			<div style="width: 150px;">
				<div class="totalshippingfeetitle">배송비</div>
				<div class="totalshippingfee"></div>
			</div>
		</div>
		<div></div>
		<div class="totalpricediv">
 			<div class="totalpricetitle" style="margin-right: 15px;">결제 예상금액</div>
			<div class="totalprice"></div>
		</div>
		<label class="shoppinglabel">* 3만원 이상 구매 시, 배송비 무료</label>
		<button class="imgdivbutton" id="shopping" onclick="Order()">주문하기</button>
	</div>		

</body>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>