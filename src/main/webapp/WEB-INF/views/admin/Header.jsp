<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>nav</title>
<link href="/resources/css/admin/Header.css" rel="stylesheet" type="text/css" />
<!-- Font Awesome 사이트 키 -->
<script src="https://kit.fontawesome.com/cacc9c0b5a.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
</head>
<script src="/resources/js/admin/Header.js"></script>
<body>
	<nav class="nav2">
		<img src="/resources/img/로고1.png" class="logo" onclick="location.href='/main'">
		<div class="nameclick">
			<label id="adminlabel">${Adminname} 님</label>
		    <img src="/resources/img/icon/아래.png" class="under">
	    </div>
	    <img src="/resources/img/icon/마이.png" class="my">
    </nav>
    <div>
	    <a href="/login/logout" id="logout">로그아웃</a>
    </div>
    <ul>
		<li><a href="/admin/memberList">회원정보</a></li>
		<li><a href="/admin/productList">상품정보</a></li>
		<li><a href="/admin/productList" class="productList">상품목록</a></li>
		<li><a href="/admin/productupload" class="productupload">상품등록</a></li>
		<li><a href="#">쿠폰</a></li>
		<li><a href="#">배송정보</a></li>
		<li><a href="#">문의</a></li>
	</ul>
</body>
</html>