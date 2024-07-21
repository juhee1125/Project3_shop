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
<script src="/resources/js/register.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/register.css" /> 
<body>
	<div class="registerP">
		<div class="title">회원가입</div>
		<input type="text" class="userName" name="userName" placeholder="이름" required> 
		<input type="text" class="userID" name="userID" placeholder="아이디" value="${userID}" required>
		<button class="duplicateB">중복확인</button>
		<label id="duplicateLabel"></label>
		<input type="password" class="userPW" name="userPW" placeholder="비밀번호" required oninput="passwordsecurity()">
		<img src="/resources/img/icon/비밀번호안보임.png" class="noPW">
		<input type="password" class="passwordcheck" name="passwordcheck" placeholder="비밀번호 확인" required>
		<label id="passwordLabel">비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이여야합니다</label>
		<input type="text" class="userPhone" name="userPhone" placeholder="핸드폰번호" required>
		<input type="Email" class="userEmail" name="userEmail" placeholder="이메일">
		<label class="emailmark">@</label>
		<input type="Email" class="emailinput" name="emailinput" placeholder="직접입력">
		<select class="emailselect">
			<option>직접입력</option>
			<option value="naver.com">naver.com</option>
			<option value="google.com">google.com</option>
			<option value="hanmail.net">hanmail.net</option>
			<option value="nate.com">nate.com</option>
			<option value="kakao.com">kakao.com</option>
		</select>
		<input type="text" id="Postalcode" name="Postalcode" placeholder="우편번호" required>
		<button id="addresssearchB" onclick="addresssearch()">검색</button>
		<input type="text" id="userAddress" name="userAddress" placeholder="주소" required>
		<input type="text" id="userdetailAddress" name="userdetailAddress" placeholder="상세주소" required>
		<button class="registerB">회원가입</button>
	</div>
</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>