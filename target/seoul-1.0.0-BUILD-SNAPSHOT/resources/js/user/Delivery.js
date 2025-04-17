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
					    statusDiv.textContent = vo.o_deliverystatus;
					
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
					    statusDiv.textContent = vo.o_deliverystatus;
					
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