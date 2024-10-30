<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/admin/Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/admin/qnaupdate.css" />
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- 팝업창 디자인 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="memberlist">
	<div class="productuploadlabel">상품문의</div>
	<div class="frame">
		<c:forEach items="${qnaclicklist}" var="vo" varStatus="status">
			<div class="namediv">
				<div>
					<label class="productname">상품명</label>
					<label class="qtitle">${vo.q_title}</label>
				</div>
				<div>
					<label class="productname">작성자</label>
					<label class="qid">${vo.q_id}</label>
				</div>
				<div>
					<label class="productname">작성일</label>
					<label class="qdate">${vo.q_date}</label>
				</div>
			</div>
			
			<div class="contentdiv">
				<div>
					<label class="productname">문의내용</label>
					<label class="qcontent">${vo.q_content}</label>
				</div>
				<c:choose>
	    			<c:when test="${not empty qnaanswer}">
						<div>
							<label class="productname">답변일</label>
							<label class="qdate">${qnarevisiondate}</label>
						</div>
					</c:when>
				</c:choose>
			</div>
			
			<c:choose>
	    		<c:when test="${empty qnaanswer}">
					<div class="answerdiv">
						<label class="productname">답변작성</label>
						<textarea class="textbox" required="required" placeholder="답변을 작성해주세요"></textarea>
					</div>
				</c:when>
				<c:otherwise>
					<div class="answerdiv">
						<label class="productname">답변작성</label>
						<textarea class="textbox" required="required">${qnaanswer}</textarea>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<button class="productbutton">등록</button>
</div>
</body>
<script src="/resources/js/admin/qnaupdate.js"></script>
</html>