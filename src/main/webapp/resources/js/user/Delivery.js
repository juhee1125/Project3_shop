document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.dateinquirybutton');
    const navigationEntries = performance.getEntriesByType("navigation");

    // 네비게이션 타입 확인 및 처리
    if (navigationEntries.length > 0) {
        const navType = navigationEntries[0].type;
        if (navType === 'reload') {
            console.log("새로 고침입니다.");
        } else if (navType === 'back_forward' || navType === 'navigate') {
            console.log("페이지 이동 후 돌아왔습니다.");
            localStorage.removeItem('selectedButton');
        }
    }

    // 로컬 스토리지에서 값 가져오기
    const savedValue = localStorage.getItem('selectedButton');
    let activeButton;

    // 초기 active 버튼 설정
    if (savedValue && savedValue !== 'undefined') {
        buttons.forEach(button => {
            if (button.textContent === savedValue) {
                button.classList.add('active');
                activeButton = button;
            }
        });
    }

    if (!activeButton) {
        buttons[0].classList.add('active');
        activeButton = buttons[0];
        localStorage.setItem('selectedButton', activeButton.textContent);
    }

	productlist=[]
	document.querySelectorAll('.oproduct').forEach(product => {
		productlist.push(product.textContent)
	});
    // Ajax 요청 보내기 함수
    const sendAjaxRequest = (button) => {
	    console.log(`Ajax 호출: ${button.textContent}`);
	    $.ajax({
	        type: 'GET',
	        url: "/delivery/deliveryinquirysearch",
	        data: { 'date': button.textContent },
			contentType: "application/json",
			dataType: 'JSON',
	        success: function (data) {
	            console.log("Ajax 성공", data);
	
	            const container = document.querySelector('#orderContainer');
	            container.innerHTML = ''; // 기존 내용을 초기화
	
	            if (data.orderlist.length === 0) {
	                // 데이터가 없는 경우 메시지 출력
	                container.innerHTML = '<label>기간 내 주문내역이 없습니다</label>';
	            } else {
					const orderMap = new Map();

					data.orderlist.forEach((vo, index) => {
					    const orderNumber = vo.o_number;
					    let mpageDiv;
						let orderDiv;
						
					    if (orderMap.has(orderNumber)) {
					        // 기존 orderDiv 가져오기
					        mpageDiv = orderMap.get(orderNumber);
							orderDiv = mpageDiv.querySelector('.order');
					    } else {
					        // 새 orderDiv 생성
					        mpageDiv = document.createElement('div');
					        mpageDiv.classList.add('mpage');
					
					        // 주문일 및 주문번호 정보
					        const infoDiv = document.createElement('div');
					        infoDiv.classList.add('info');
					
					        const dateDiv = document.createElement('div');
					        const orderDate = new Date(vo.o_date);
					        dateDiv.textContent = orderDate.toLocaleDateString().replace(/\.$/, '');
					
					        const numberDiv = document.createElement('div');
					        numberDiv.classList.add('onumber');
					        numberDiv.textContent = orderNumber;
							numberDiv.onclick = function() { 
								location.href = '/delivery/deliverydetail?orderNumber=' + orderNumber; 
							 };
					
					        infoDiv.appendChild(dateDiv);
					        infoDiv.appendChild(numberDiv);
					        mpageDiv.appendChild(infoDiv);
					
					        orderMap.set(orderNumber, mpageDiv);
					        container.appendChild(mpageDiv);
					    }
						if (!orderDiv) {
			                orderDiv = document.createElement('div');
			                orderDiv.classList.add('order');
			                mpageDiv.appendChild(orderDiv);
			            }
					    // 개별 상품 정보를 위한 orderDetailDiv 생성
					    const orderDetailDiv = document.createElement('div');
					    orderDetailDiv.classList.add('orderdetail'); // 개별 상품을 담는 div
					
					    // 상품 정보 추가
					    const productinfoDiv = document.createElement('div');
					    productinfoDiv.classList.add('oproductinfo');
					
					    const productDiv = document.createElement('div');
					
					    const productnameDiv = document.createElement('div');
					    productnameDiv.classList.add('oproduct');
					    productnameDiv.textContent = data.productnamelist[index];
					
					    const optionLabel = document.createElement('label');
					    const priceDiv = document.createElement('div');
					    priceDiv.classList.add('oprice');
					
					    if (vo.o_option == null) {
					        optionLabel.textContent = "";
					        priceDiv.textContent = vo.o_totalprice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "원";
					    } else {
					        optionLabel.textContent = vo.o_option + " " + vo.o_optiondetail;
					        priceDiv.textContent = vo.o_optionprice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "원";
					    }
					
					    productDiv.appendChild(productnameDiv);
					    productDiv.appendChild(optionLabel);
					
					    const productImg = document.createElement('img');
					    productImg.classList.add('oimage');
					    productImg.src = vo.o_image;
					
					    productinfoDiv.appendChild(productImg);
					    productinfoDiv.appendChild(productDiv);
					
					    const statusDiv = document.createElement('div');
					    statusDiv.classList.add('odeliverystatus');
					    statusDiv.textContent = vo.o_paymentstatus;
					
					    const quantityDiv = document.createElement('div');
					    quantityDiv.classList.add('oquantity');
					    quantityDiv.textContent = vo.o_quantity;
					
					    // 상품 정보를 orderDetailDiv에 추가
					    orderDetailDiv.appendChild(productinfoDiv);
					    orderDetailDiv.appendChild(quantityDiv);
					    orderDetailDiv.appendChild(priceDiv);
					    orderDetailDiv.appendChild(statusDiv);
					
					    // 주문별 orderDiv 안에 상품별 orderDetailDiv 추가
						orderDiv.appendChild(orderDetailDiv);
					    mpageDiv.appendChild(orderDiv);
					});
	            }
	        },
	        error: function (error) {
	            console.error("Ajax 오류 발생", error);
	        }
	    });
	};

    // 초기 active 버튼으로 Ajax 호출
    if (activeButton) {
        sendAjaxRequest(activeButton);
    }

    // 버튼 클릭 이벤트 추가
    buttons.forEach(button => {
        button.addEventListener('click', () => {
            buttons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            localStorage.setItem('selectedButton', button.textContent);

            sendAjaxRequest(button); // 클릭된 버튼으로 Ajax 요청
        });
    });
});


$(document).ready(function() {
    $('.inquirybutton').click(function() {
		localStorage.removeItem('selectedButton');
        //날짜입력 시 조회
		let startDateInput = document.querySelector('[name="dateinputstart"]');
	    let endDateInput = document.querySelector('[name="dateinputend"]');
		
		console.log(startDateInput.value, endDateInput.value)
		$.ajax({
	        type: 'GET',
	        url: "/delivery/directinquirysearch",
	        data: { 
				'startDateInput': startDateInput.value,
			 	'endDateInput': endDateInput.value
			},
			contentType: "application/json",
			dataType: 'JSON',
	        success: function (data) {
	            console.log("Ajax 성공", data);
	
	            const container = document.querySelector('#orderContainer');
	            container.innerHTML = ''; // 기존 내용을 초기화
	
	            if (data.orderlist.length === 0) {
	                // 데이터가 없는 경우 메시지 출력
	                container.innerHTML = '<label>기간 내 주문내역이 없습니다</label>';
	            } else {
					const orderMap = new Map();

					data.orderlist.forEach((vo, index) => {
					    const orderNumber = vo.o_number;
					    let mpageDiv;
						let orderDiv;
						
					    if (orderMap.has(orderNumber)) {
					        // 기존 orderDiv 가져오기
					        mpageDiv = orderMap.get(orderNumber);
							orderDiv = mpageDiv.querySelector('.order');
					    } else {
					        // 새 orderDiv 생성
					        mpageDiv = document.createElement('div');
					        mpageDiv.classList.add('mpage');
					
					        // 주문일 및 주문번호 정보
					        const infoDiv = document.createElement('div');
					        infoDiv.classList.add('info');
					
					        const dateDiv = document.createElement('div');
					        const orderDate = new Date(vo.o_date);
					        dateDiv.textContent = orderDate;
					
					        const numberDiv = document.createElement('div');
					        numberDiv.classList.add('onumber');
					        numberDiv.textContent = orderNumber;
							numberDiv.onclick = function() { 
								location.href='/delivery/deliverydetail';
							 };
					
					        infoDiv.appendChild(dateDiv);
					        infoDiv.appendChild(numberDiv);
					        mpageDiv.appendChild(infoDiv);
					
					        orderMap.set(orderNumber, mpageDiv); // Map에 저장
					        container.appendChild(mpageDiv); // 처음 추가할 때만 컨테이너에 추가
					    }
						if (!orderDiv) {
			                orderDiv = document.createElement('div');
			                orderDiv.classList.add('order');
			                mpageDiv.appendChild(orderDiv);
			            }
					    // 개별 상품 정보를 위한 orderDetailDiv 생성
					    const orderDetailDiv = document.createElement('div');
					    orderDetailDiv.classList.add('orderdetail'); // 개별 상품을 담는 div
					
					    // 상품 정보 추가
					    const productinfoDiv = document.createElement('div');
					    productinfoDiv.classList.add('oproductinfo');
					
					    const productDiv = document.createElement('div');
					
					    const productnameDiv = document.createElement('div');
					    productnameDiv.classList.add('oproduct');
					    productnameDiv.textContent = data.productnamelist[index];
					
					    const optionLabel = document.createElement('label');
					    const priceDiv = document.createElement('div');
					    priceDiv.classList.add('oprice');
					
					    if (vo.o_option == null) {
					        optionLabel.textContent = "";
					        priceDiv.textContent = vo.o_totalprice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "원";
					    } else {
					        optionLabel.textContent = vo.o_option + " " + vo.o_optiondetail;
					        priceDiv.textContent = vo.o_optionprice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "원";
					    }
					
					    productDiv.appendChild(productnameDiv);
					    productDiv.appendChild(optionLabel);
					
					    const productImg = document.createElement('img');
					    productImg.classList.add('oimage');
					    productImg.src = vo.o_image;
					
					    productinfoDiv.appendChild(productImg);
					    productinfoDiv.appendChild(productDiv);
					
					    const statusDiv = document.createElement('div');
					    statusDiv.classList.add('odeliverystatus');
					    statusDiv.textContent = vo.o_paymentstatus;
					
					    const quantityDiv = document.createElement('div');
					    quantityDiv.classList.add('oquantity');
					    quantityDiv.textContent = vo.o_quantity;
					
					    // 상품 정보를 orderDetailDiv에 추가
					    orderDetailDiv.appendChild(productinfoDiv);
					    orderDetailDiv.appendChild(quantityDiv);
					    orderDetailDiv.appendChild(priceDiv);
					    orderDetailDiv.appendChild(statusDiv);
					
					    // 주문별 orderDiv 안에 상품별 orderDetailDiv 추가
						orderDiv.appendChild(orderDetailDiv);
					    mpageDiv.appendChild(orderDiv);
					});
	            }
	        },
	        error: function (error) {
	            console.error("Ajax 오류 발생", error);
	        }
	    });
    });
});


/* 결제취소 */
function Cancelpopupopen(){
	const mpageElements = document.querySelectorAll(".mpage");
	const totalpricesubdiv = document.querySelectorAll(".totalpricesubdiv")[1];
	
	let checkboxHTML = "";
	let totalprice =0;
	let customerpnames;
	let customerpname=0;
	let productName;
	
	mpageElements.forEach((mpage, index) => {
	  productName = mpage.querySelector(".productnamelabel").textContent;
	  const quantity = mpage.querySelector(".oquantity").textContent;
	  const price = mpage.querySelector(".oprice").textContent;
	  const option = mpage.querySelector(".productoptionlabel");
	  const optiondetail = mpage.querySelector(".productdetaillabel");
	  let optionlabel ="";
	  let optiondetaillabel="";
	  if(option!=null){
		optionlabel = option.textContent;
		optiondetaillabel = optiondetail.textContent;
	  }
	  const pnum = mpage.getAttribute("data-product-num"); // 데이터 속성 있으면

	  customerpnames = totalpricesubdiv.querySelector(".pricesubdiv");
	  customerpname = parseInt(customerpnames.textContent.replace(/[^\d]/g, ''), 10);
	  totalprice += parseInt(price.replace(/[^\d]/g, ''), 10);
	  console.log(totalprice)
	  if(customerpnames.id == productName){
		checkboxHTML += `
		    <div style="text-align: left;display: flex;margin-bottom: 30px;">
				<input type="checkbox" class="refund-checkbox" value="${index}" data-pnum="${pnum}" style="margin-right: 25px;" checked>
				<div>
					<label>
						<strong>${productName}</strong> ${optionlabel} ${optiondetaillabel} </br> 수량 ${quantity} 금액 ${price}
					</label>						
					<div style="color: #b3b3b3;">
						<label>할인금액</label>
						<label>${customerpname.toLocaleString('ko-KR')}원</label>								
					</div>
				</div>
		    </div>
		`;
	  }
	  else{
		checkboxHTML += `
		    <div style="margin-bottom: 30px;text-align: left;display: flex;">
				<input type="checkbox" class="refund-checkbox" value="${index}" data-pnum="${pnum}" style="margin-right: 25px;" checked>
				<label>
					<strong>${productName}</strong> ${optionlabel} ${optiondetaillabel} </br> 수량 ${quantity} 금액 ${price}
				</label>
		    </div>
		`;
	  }
	});
	if(customerpnames.id == productName){
		checkboxHTML += `
			<div style="font-size: 20px;">
				<strong>총 결제금액 ${(totalprice-customerpname).toLocaleString('ko-KR')}원</strong>
			</div>
		`;
	} else{
		checkboxHTML += `
			<div style="font-size: 20px;">
				<strong>총 결제금액 ${totalprice.toLocaleString('ko-KR')}원</strong>
			</div>
		`;
	}
	
	Swal.fire({
	  title: '환불할 상품 선택',
	  html: checkboxHTML,
	  showCancelButton: true,
	  confirmButtonText: '환불 요청',
	  cancelButtonText: '취소',
	  preConfirm: () => {
	    const selected = Array.from(document.querySelectorAll('.refund-checkbox:checked'))
	      .map(cb => ({
	        index: cb.value,
	        pnum: cb.dataset.pnum
	      }));
	    
	    if (selected.length === 0) {
	      Swal.showValidationMessage("최소 한 개의 상품을 선택해주세요.");
	    }
	    return selected;
	  }
	}).then(result => {
	  if (result.isConfirmed) {
		//객체를 배열로 변환
		console.log("result.value:", result.value);
		const selectedItems = result.value.map(item => item.pnum);
	    console.log("환불 요청 상품 목록:", selectedItems);

		
		$.ajax({
			type: 'Post',
	        url: "/refund/refundprocess",
	        data: JSON.stringify(selectedItems),
		    contentType: "application/json",
	        success: function (data) {
				console.log("성공")
				refundsuccess("환불처리가 완료되었습니다.")
	        }
	    });
	  }
	});
}
function refundsuccess(message) {	
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    })
};