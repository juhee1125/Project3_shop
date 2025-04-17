<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>nav</title>
<link href="/resources/css/nav.css" rel="stylesheet" type="text/css" />
<!-- Font Awesome 사이트 키 -->
<script src="https://kit.fontawesome.com/cacc9c0b5a.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
</head>
<script src="/resources/js/nav.js"></script>
<body>
	<nav class="nav1">
		<c:choose>
			<c:when test="${empty sessionScope.loginMember}">
				<a href="/login/login" id="loginPage">로그인</a>
		    	<a href="/login/register" id="registerPage">회원가입</a>
			</c:when>
			<c:otherwise>
				<a href="/login/logout" id="logout">로그아웃</a>
			</c:otherwise>
		</c:choose>
	    <img src="/resources/img/icon/마이.png" class="my" onclick="location.href='/my/my'">
    </nav>
    <nav class="nav2">
	    <img src="/resources/img/로고1.png" class="logo-mobile" onclick="location.href='/main'">
	    <a href="/main" class="logo">Brillux</a>
	    <a href="/shop/skin" id="skin">스킨케어</a>
	    <a href="/shop/sun" id="sun">선케어</a>
	    <a href="/shop/base" id="base">베이스 메이크업</a>
	    <a href="/shop/eye" id="eye">아이 메이크업</a>
	    <a href="/shop/lip" id="lip">립 메이크업</a>
	    <div class="search">
		    <form action="#" method="post" id="searchForm">
		        <input type="text" id="searchKeyword" name="searchKeyword" style="font-family:'noto-reg';">
		        <button type="button" onclick="search()" type="submit" class="search">
		        	<i class="fa-solid fa-magnifying-glass"></i>
		        </button>
		    </form>
    	</div>
		<img src="/resources/img/icon/찜.png" onclick="location.href='/like/like'" class="heart">
		<img src="/resources/img/icon/장바구니.png" onclick="location.href='/order/shopping'" class="shopping">

     </nav>
     <nav class="bottomnav">
     	<img src="/resources/img/icon/메뉴.png" class="submenu" onclick="menuopen()">
     	<img src="/resources/img/icon/마이.png" class="mybottom">
     </nav>
     <div id="menu-overlay" class="menu-overlay">
        <div class="menu-content">
            <a href="javascript:void(0)" class="closebtn" onclick="toggleMenu()">&times;</a>
            <a href="/shop/skin" id="skin">스킨케어</a>
	    	<a href="/shop/sun" id="sun">선케어</a>
	    	<a href="/shop/base" id="base">베이스 메이크업</a>
		    <a href="/shop/eye" id="eye">아이 메이크업</a>
		    <a href="/shop/lip" id="lip">립 메이크업</a>
        </div>
    </div>
</body>
</html>