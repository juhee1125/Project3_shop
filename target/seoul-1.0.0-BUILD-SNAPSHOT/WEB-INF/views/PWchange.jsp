<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/Header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<script src="/resources/js/PWchange.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/PWchange.css" /> 
<body>
	<div class="PWchangeP">
	<div class="title">비밀번호 변경</div>
	<input type="password" class="userPW" name="userPW" placeholder="새 비밀번호" oninput="passwordsecurity()">
	<img src="/resources/img/icon/비밀번호안보임.png" class="noPW">
	<input type="password" class="passwordcheck" name="passwordcheck" placeholder="새 비밀번호 확인">
	<label id="passwordLabel">비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이여야합니다</label>
	<button class="changePWB">로그인하러가기</button>
	</div>
</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>