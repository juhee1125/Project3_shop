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
<link rel="stylesheet" type="text/css" href="/resources/css/user/ShopDetail.css" /> 
<script src="/resources/js/user/Main.js"></script>
<script src="/resources/js/user/Skin.js"></script>
<script src="/resources/js/user/ShopDetail.js"></script>
<body>
	<div class="container">
		<div>
			<c:forEach var="pathimage" items="${productpathlist}" varStatus="status">
		    	<c:if test="${status.index == 0}">
		    		<img src="/admin/${pathimage}" class="mainimage">
		    	</c:if>
		    	<c:if test="${status.index > 0}">
		    		<img src="/admin/${pathimage}" class="subimage">
		    	</c:if>
	    	</c:forEach>
		</div>
	    <div class="infodiv">
        	<label class="infodivlabel">${productlist.p_name}</label>
			<div class="infodivprice">
				<c:choose>
					<c:when test="${productlist.p_discount != null}">
						<label class="discounted-price">
							<fmt:formatNumber value="${productlist.p_price}" type="number" groupingUsed="true"/>원
						</label>
						<label class="price">
							<fmt:formatNumber value="${productlist.p_price-(productlist.p_price * (productlist.p_discount / 100))}" type="number" maxFractionDigits="0"/>원
						</label>
					</c:when>
					<c:otherwise>
						<div class="pricediv">
							<label class="price">
							<fmt:formatNumber value="${productlist.p_price}" type="number" maxFractionDigits="0"/>원
						</label>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
	        <c:choose>
		    	<c:when test="${productoptionlist.size() > 0}">
			        <select class="infodivselect" onchange="clickoption(this)">
			        	<option>상품을 선택해주세요</option>
				        <c:forEach var="option" items="${productoptionlist}" varStatus="status"> 	
			        		<option data-option="${option.po_option}" data-optiondetail="${option.po_optiondetail}">
			        		${option.po_option}${option.po_optiondetail}</option>
				        </c:forEach>
				    </select>
				</c:when>
				<c:otherwise>
					<div class="totalquantity">
						<label>구매수량</label>
						<div class="buttonDiv">
							<button class="minusBtn"></button>
							<input class="quantityinput" value="1"></input>
							<button class="plusBtn"></button>
						</div>
            		</div>
				</c:otherwise>
			</c:choose>		
            <div id="options-container"></div>	
            <c:choose>
		    	<c:when test="${productoptionlist.size() > 0}">
		    		<div class="totalprice">
	                	<label class="totallabel">총액</label>
	                	<label class="totalpricelabel">
                   			<fmt:formatNumber value="0" type="number" maxFractionDigits="0"/>원
                   		</label>
		            </div> 
		    	</c:when>
		    	<c:otherwise>
		    		<div class="totalprice">
	                	<label class="totallabel">총액</label>
		                <c:choose>
		                    <c:when test="${productlist.p_discount != null}">
		                        <label class="totalpricelabel">
		                        	<fmt:formatNumber value="${productlist.p_price-(productlist.p_price * (productlist.p_discount / 100))}" type="number" maxFractionDigits="0"/>원
								</label>
		                    </c:when>
		                    <c:otherwise>
	                    		<label class="totalpricelabel">
	                    			<fmt:formatNumber value="${productlist.p_price}" type="number" maxFractionDigits="0"/>원
	                    		</label>
		                    </c:otherwise>
		                </c:choose>
		            </div> 
		    	</c:otherwise>
		    </c:choose>  
			<div class="imgdiv">
				<button class="imgdivbutton" id="shopping">장바구니</button>
				<button class="imgdivbutton">바로구매</button>
                <img src="${heartImgSrc}" class="heartimg liked">
            </div>
	    </div>
	</div>
	<div class="tabcontainer">
		<ul class="tabs">
		  <li class="tab-link current" data-tab="tab-1">상품설명</li>
		  <li class="tab-link" data-tab="tab-2">리뷰</li>
		  <li class="tab-link" data-tab="tab-3">Q&A</li>
		</ul>
		
		<div id="tab-1" class="tab-content current">
			<img src="/admin/${productdetailpath}" class="tab-content-img">
		</div>
		<div id="tab-2" class="tab-content">tab content2</div>
		<div id="tab-3" class="tab-content">
			<div class="firsttabdiv">
				<label class="tablabel">교환/반품 등 자세한 문의는 1:1 문의를 이용해주세요</label>
				<button class="tabbutton">상품문의</button>
			</div>
			<div class="QnApopup" ></div>
			<div>
				<c:choose>
				    <c:when test="${not empty qnalistlabel}">
				        <label class="qnalistlabel">${qnalistlabel}</label>
				    </c:when>
				    <c:otherwise>
				        <c:forEach var="qna" items="${QnAlistdetail}" varStatus="status">
				        	<div class="qnalistdiv">
					        	<c:choose>
									<c:when test="${qna.q_answerstatus == '답변완료'}">
										<label class="qnastatus_answered">${qna.q_answerstatus}</label>
										<label class="qnaq_content" onclick="clickqnacontent(${status.index})">${qna.q_content}</label>
									</c:when>
									<c:otherwise>
										<label class="qnastatus">${qna.q_answerstatus}</label>
										<label class="qnaq_content">${qna.q_content}</label>
									</c:otherwise>
								</c:choose>
 				                <label>${QnAUser[status.index].m_id}</label>
				                <label>${qna.q_date}</label>
				                <div class="qnaDetail" id="detail-${status.index}" style="display: none;"></div>
				        	</div>
				        	<div class="answerlistdiv" id="answer-${status.index}">
				        	</div>
				        </c:forEach>
				    </c:otherwise>
				</c:choose>
	        </div>
		</div>
	</div>
    <!-- 맨위로 버튼 -->
    <a id="btn_gotop" href="#">
        <img src="resources/img/icon/up-arrow-angle.png" id="up">
    </a>
</body>
<script>
    // JSON.parse로 전달된 값을 안전하게 파싱
    const QnAAnswer = JSON.parse('${QnAAnswer}');
    console.log(QnAAnswer);  // 값이 제대로 전달되었는지 확인
</script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
</html>