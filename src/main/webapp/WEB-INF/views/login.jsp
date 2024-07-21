<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/Header.jsp" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<script src="/resources/js/login.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/login.css" /> 
<body>
	<div class="loginP">
		<div class="title">로그인</div>
		<input type="text" class="userID" name="userID" placeholder="아이디" value="">
		<input type="password" class="userPW" name="userPW" placeholder="비밀번호">
        <label for="remember-check" style="position: absolute; top: 28em; left: 50em; font-size: 15px; font-family: 'noto-reg';">
            <input type="checkbox" id="remember-check" onclick="IDremember()">아이디 저장하기
        </label>
		<button class="loginB" data-error="${sessionScope.error}">로그인</button>
		<a href="/login/register" class="register">회원가입</a>
		<a href="/login/findID" class="IDfind">아이디 찾기</a>
		<a href="/login/findPW" class="PWfind">비밀번호 찾기</a>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>