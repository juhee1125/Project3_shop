<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/productupdate.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<script src="/resources/js/admin/productupdate.js"></script>
<body>
<div class="memberlist">
	<div class="productuploadlabel">상품수정</div>
	<div class="frame">
		<div style="margin-bottom: 21em;">
			<label class="productinformation">상품정보</label>
			<div class="line1"></div>
	 		<label class="productname">상품명*</label>
			<input type="text" class="nameinput" value="${productupdatelist.p_name}"></input>
			<label class="productprice">가격*</label>
			<input type="text" class="priceinput" value="${productupdatelist.p_price}"></input>
			<label class="productquantity">전체수량*</label>
			<input type="number" class="quantityinput" min="0" max="999" value="${productupdatelist.p_quantity}"></input>
			<label class="productdiscount">할인</label>
			<div class="line2"></div>
			<label class="discountpercent">할인율</label>
			<input type="text" class="percentinput" value="${productupdatelist.p_discount}"></input>
			<label class="percent">%</label>
			<label class="discountdate">할인기간</label>
			<input type="date"id="dateinputstart" data-placeholder="시작일" required aria-required="true" value="${productupdatelist.p_discount_start}"></input>
			<label class="from">~</label>
			<input type="date" id="dateinputend" data-placeholder="종료일" required aria-required="true" value="${productupdatelist.p_discount_end}"></input>
		</div>
		<label class="productoption">옵션</label>
		<div class="line3"></div>
		<div id="options-container">
	        <c:choose>
		        <c:when test="${not empty optionlist}">
		            <c:forEach var="index" items="${optionlist}" varStatus="loop">
		                <div class="option-group">
		                    <input type="text" class="optioninput" placeholder="옵션명" value="${optionlist[loop.index]}">
		                    <input type="text" class="optiondetailinput" placeholder="옵션정보" value="${optiondetaillist[loop.index]}">
		                    <img src="/resources/img/icon/플러스.png" class="plus" onclick="addOption()">
		                </div>
		            </c:forEach>
		        </c:when>
		        <c:otherwise>
		        	<div class="option-group">
	                    <input type="text" class="optioninput" placeholder="옵션명">
	                    <input type="text" class="optiondetailinput" placeholder="옵션정보">
	                    <img src="/resources/img/icon/플러스.png" class="plus" onclick="addOption()">
	                </div>
		    	</c:otherwise>
		    </c:choose>
	    </div>
		<c:set var="selectedCategory" value="${productupdatelist.p_category}" />
		<label class="category">카테고리*</label>
		<div class="category-group">
		    <input type="radio" class="categoryradio" name="categoryradio" value="skin" <c:if test="${selectedCategory == 'skin'}">checked</c:if>>
		    <label class="categorylabel">스킨케어</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="sun" <c:if test="${selectedCategory == 'sun'}">checked</c:if>>
		    <label class="categorylabel">선케어</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="base" <c:if test="${selectedCategory == 'base'}">checked</c:if>>
		    <label class="categorylabel">베이스메이크업</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="eye" <c:if test="${selectedCategory == 'eye'}">checked</c:if>>
		    <label class="categorylabel">아이메이크업</label>
		
		    <input type="radio" class="categoryradio" name="categoryradio" value="lip" <c:if test="${selectedCategory == 'lip'}">checked</c:if>>
		    <label class="categorylabel">립메이크업</label>
		</div>
	</div>
	<div class="frame">
		<label class="mainimage">메인이미지*</label>
		<label class="mainimagelabel">상품 대표로 보여지는 이미지입니다. 최대 5개 업로드 가능합니다.</label>
		<div id="previewImg">
			<c:forEach var="index" items="${pathlist}" varStatus="loop">
				<img src="/admin/${pathlist[loop.index]}" class="mainimagestyle">
				<button class="close-button">
					<img src="/resources/img/icon/엑스.png" class="closeimage">
				</button>
			</c:forEach>
		</div>
		<form id="uploadForm" enctype="multipart/form-data">
			<label id="imageFilelabel" for="imageFile">이미지 업로드</label>
			<input type="file" id="imageFile" name="imageFile" accept="image/*" multiple>
		</form>
	</div>
	<div class="frame">
		<div class="upload-group">
			<label class="mainimage">상세이미지*</label>
		</div>	
		<div id="previewdetail">
			<img id="previewdetailImg" src="/admin/${productdetailpath}">
			<button class="close-detailbutton">
				<img src="/resources/img/icon/엑스.png" class="closedetailimage">
			</button>
		</div>
		<form id="uploaddetailForm" enctype="multipart/form-data">
			<label id="imageFilelabel" for="imagedetailFile">이미지 업로드</label>
			<input type="file" id="imagedetailFile" name="imagedetailFile" accept="image/*">	
		</form>
	</div>
	<button class="productbutton">수정</button>
</div>
</body>
<script src="/resources/js/admin/memberList.js"></script>
</html>