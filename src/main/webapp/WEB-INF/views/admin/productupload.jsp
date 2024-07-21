<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/productupload.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<script src="/resources/js/admin/productupload.js"></script>
<body>
<div class="memberlist">
    <div class="productuploadlabel">상품등록</div>
    <div class="frame">
        <div style="margin-bottom: 21em;">
            <label class="productinformation">상품정보</label>
            <div class="line1"></div>
            <label class="productname">상품명*</label>
            <input type="text" class="nameinput" id="nameinput" name="nameinput" required>
            <label class="productprice">가격*</label>
            <input type="text" class="priceinput" id="priceinput" name="priceinput" required>
            <label class="productquantity">전체수량*</label>
            <input type="number" class="quantityinput" id="quantityinput" name="quantityinput" min="0" max="999" required>
            <label class="productdiscount">할인</label>
            <div class="line2"></div>
            <label class="discountpercent">할인율</label>
            <input type="text" class="percentinput" id="percentinput" name="percentinput">
            <label class="percent">%</label>
            <label class="discountdate">할인기간</label>
            <input type="date" id="dateinputstart" name="dateinputstart" required>
            <label class="from">~</label>
            <input type="date" id="dateinputend" name="dateinputend" required>
        </div>
        <label class="productoption">옵션</label>
        <div class="line3"></div>
        <div id="options-container">
            <div class="option-group">
                <input type="text" class="optioninput" name="options[0].optioninput" placeholder="옵션명">
                <input type="text" class="optiondetailinput" name="options[0].optiondetailinput" placeholder="옵션정보">
                <img src="/resources/img/icon/플러스.png" class="plus" onclick="addOption()">
            </div>
        </div>
        <label class="category">카테고리*</label>
        <div class="category-group">
            <input type="radio" class="categoryradio" id="categoryradio1" name="categoryradio" value="skin">
            <label class="categorylabel" for="categoryradio1">스킨케어</label>
            <input type="radio" class="categoryradio" id="categoryradio2" name="categoryradio" value="sun">
            <label class="categorylabel" for="categoryradio2">선케어</label>
            <input type="radio" class="categoryradio" id="categoryradio3" name="categoryradio" value="base">
            <label class="categorylabel" for="categoryradio3">베이스메이크업</label>
            <input type="radio" class="categoryradio" id="categoryradio4" name="categoryradio" value="eye">
            <label class="categorylabel" for="categoryradio4">아이메이크업</label>
            <input type="radio" class="categoryradio" id="categoryradio5" name="categoryradio" value="lip">
            <label class="categorylabel" for="categoryradio5">립메이크업</label>
        </div>
    </div>
    <div class="frame">
        <label class="mainimage">메인이미지*</label>
        <label class="mainimagelabel">상품 대표로 보여지는 이미지입니다. 최대 5개 업로드 가능합니다.</label>
        <div id="previewImg"></div>
        <form id="uploadForm" enctype="multipart/form-data">
            <label id="imageFilelabel" for="imageFile">이미지 업로드</label>
            <input type="file" id="imageFile" name="imageFile" accept="image/*" multiple>
        </form>
    </div>
    <div class="frame">
        <div class="upload-group">
            <label class="mainimage">상세이미지*</label>
        </div>    
        <img id="previewdetailImg" src="">
        <form id="uploaddetailForm" enctype="multipart/form-data">
            <label id="imageFilelabel" for="imagedetailFile">이미지 업로드</label>
            <input type="file" id="imagedetailFile" name="imagedetailFile" accept="image/*">    
        </form>
    </div>
    <button class="productbutton">등록</button>
</div>
</body>
<script src="/resources/js/admin/memberList.js"></script>
</html>