/* 전체선택 */
function selectAll(selectAll)  {
  const checkboxes = document.querySelectorAll('.listcheckbox');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked;
  })
}

/* select박스에 따른 권한변경 */
function AdminAction() {
    const action = document.querySelector('.adminAction').value;
    const order = document.querySelector('.imgdivbutton');
	
	if (action===""){
		actionwarningalert('수정하고자 하는 항폭을 선택해주세요');
	}
	 const products = [];
	
	/* 체크박스 선택여부 */
	const checkboxes = document.querySelectorAll('.listcheckbox:checked');
    if (checkboxes.length === 0) {
        actionwarningalert('수정하고자 하는 상품을 선택해주세요');
		return;
	}
	/* 체크박스와 제일 근접한 상위클래스에서 추출하고자 하는 클래스 확인 */
	checkboxes.forEach(checkbox => {
        const mpage = checkbox.closest('.mpage');
		if (mpage) {
            const ordernum = mpage.getAttribute('data-order-num');
            if (ordernum) {
                products.push({
                    ordernum: ordernum,
                });
            } else {
                console.error('ordernum 요소를 찾을 수 없습니다.');
            }
        } else {
            console.error('mpage 요소를 찾을 수 없습니다.');
        }
	})
	/* 상품삭제 */
	if (action === "productdelete") {
        deleteToProduct(products);
    }
};
function actionsuccessalert(message) {
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
		location.reload();
	})
};
function actionwarningalert(message) {
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    })
};
function deleteToProduct(users) {
    $.ajax({
        type: 'Post',
        url: '/order/productdelete',
		contentType: "application/json",
        data: JSON.stringify(users),
		dataType: 'text',
        success: function (data) {
            actionsuccessalert(data);
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
}

/* 상품명 클릭 시 상세페이지 */
function clickname(event) {
	const optiondiv = event.target;
	const ordernum = optiondiv.closest('.mpage').getAttribute('data-order-num');
    $.ajax({
        type: 'Post',
        url: "/order/pagemove",
        data: { 
			'ordernum': ordernum
		},
		dataType: 'json',
        success: function (data) {
			window.location.href = "/detail/detail?num=" + encodeURIComponent(data);
        },
        error: function (error) {
            console.error("오류발생");
        }
    });
}

/* 옵션수정 */
function optionchange(event){
	const optiondiv = event.target;
	// 상품명
    const pnamediv = optiondiv.closest('.pnamediv').querySelector('div:first-child');
	const pname = pnamediv.textContent.trim();
	// 옵션
	const selectoptiondiv = optiondiv.closest('.optiondiv').querySelector('div:first-child');
	const ordernum = optiondiv.closest('.mpage').getAttribute('data-order-num');

	$.ajax({
        type: 'Post',
        url: '/order/optionmodify',
        data: { pname: pname },
		dataType: 'json',
        success: function (response) {
			const optionlistselect = document.createElement('select');
			optionlistselect.classList.add('optionlistselect');
			response.forEach(item => {
				const optionlistoption = document.createElement('option');
				
				optionlistoption.textContent = item.option +" "+ item.optiondetail;
				optionlistoption.value = item.option +"|"+ item.optiondetail;
				optionlistselect.append(optionlistoption);
				
				if (optionlistoption.textContent.trim() === selectoptiondiv.textContent.trim()) {
				    optionlistoption.selected = true;
				};
			});
			const ordertotalprice = optiondiv.closest('.mpage').querySelector('.buyprice').textContent.replace(/[^0-9]/g, '');
			const quantityinput = optiondiv.closest('.mpage').querySelector('.quantityinput').value;
			optionchangealert(optionlistselect, ordertotalprice, quantityinput, ordernum);
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
}
function optionchangealert(optionlistselect, ordertotalprice, quantityinput, ordernum) {
	Swal.fire({
        html: optionlistselect,
        confirmButtonText: '수정',
        showCancelButton: true,
        cancelButtonText: '취소'
    }).then(function(result) {
        if (result.isConfirmed) {
			const optionselect = optionlistselect.value;
			const [selectOption, selectOptionDetail] = optionselect.split("|");
			
	        $.ajax({
		        type: 'Post',
		        url: '/order/optionupdate',
				contentType: "application/json",
		        data: JSON.stringify(
					{"selectOption":selectOption,
					"selectOptionDetail":selectOptionDetail,
					 "ordertotalprice":ordertotalprice,
					 "quantityinput":quantityinput,
					 "ordernum":ordernum}
				),
				dataType: 'text',
		        success: function (data) {
		            location.reload();
					console.log(selectOption,",",selectOptionDetail)
		        },
		        error: function () {
		            alert('오류가 발생했습니다.');
		        }
		    });
		};
    });
};

/* 결제금액 계산 */
$(document).ready(function () {
    const checkboxes = document.querySelectorAll('.listcheckbox');
	console.log(document.querySelector('.listcheckbox').checked);

    checkboxes.forEach(checkbox => {
		let ordertotalprice = 0;
		let buyprice = 0;
        document.querySelectorAll('#checkbox:checked').forEach((checkedBox) => {
            const orderprice = checkedBox.closest('.mpage').querySelector('.buyprice').textContent.replace(/[^0-9]/g, '');
			ordertotalprice += parseInt(orderprice, 10);
			
			//수량
			const quantity = checkedBox.closest('.mpage').querySelector('.quantityinput').value;
			const price = checkedBox.closest('.mpage').querySelector('.price').textContent.replace(/[^0-9]/g, '');
			buyprice = parseInt(quantity) * parseInt(price);
			checkedBox.closest('.mpage').querySelector('.buyprice').textContent = buyprice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")+"원";
			
        });

		document.querySelector('.ordertotalprice').textContent = ordertotalprice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")+"원";
		
		if (ordertotalprice >= 30000 || ordertotalprice == 0){
			document.querySelector('.totalshippingfee').textContent = "0원";
		}
		else{
			document.querySelector('.totalshippingfee').textContent = "5,000원";
		}
		
		totalpriceplus = parseInt(ordertotalprice) + parseInt(document.querySelector('.totalshippingfee').textContent.replace(/[^0-9]/g, ''));
		console.log(typeof(totalpriceplus));
		if(totalpriceplus)
		document.querySelector('.totalprice').textContent = totalpriceplus.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")+"원";

		//수정 시
        checkbox.addEventListener('change', function () {
            let ordertotalprice = 0;
	        document.querySelectorAll('#checkbox:checked').forEach((checkedBox) => {
	            const orderprice = checkedBox.closest('.mpage').querySelector('.buyprice').textContent.replace(/[^0-9]/g, '');
				ordertotalprice += parseInt(orderprice, 10);
				
	        });
			document.querySelector('.ordertotalprice').textContent = ordertotalprice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")+"원";
			
			if (ordertotalprice >= 30000 || ordertotalprice == 0){
				document.querySelector('.totalshippingfee').textContent = "0원";
			}
			else{
				document.querySelector('.totalshippingfee').textContent = "5,000원";
			}
			
			totalpriceplus = parseInt(ordertotalprice) + parseInt(document.querySelector('.totalshippingfee').textContent.replace(/[^0-9]/g, ''));
			document.querySelector('.totalprice').textContent = totalpriceplus.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")+"원";
        });
    });	
});

$(document).ready(function () {
	/* 수량감소 */
	document.querySelectorAll(".minusBtn").forEach(function (btn) {
		btn.addEventListener("click", function (event) {
			let mpage = event.target.closest(".mpage");
            let quantityInput = mpage.querySelector(".quantityinput");
            let quantity = parseInt(mpage.querySelector(".quantityinput").value);
            let buyprice = mpage.querySelector(".buyprice").textContent;
			const price = mpage.querySelector('.price').textContent.replace(/[^0-9]/g, '');
			const ordernum = mpage.getAttribute('data-order-num');
			
			if (quantity-1 < 1){
				return
			}
			quantity--;
			quantityInput.value = quantity;
			buyprice = parseInt(price) * quantity;
			console.log(buyprice)
			$.ajax({
		        type: 'Post',
		        url: '/order/quantitymodify',
				contentType: "application/json",
		        data: JSON.stringify(
					{"ordernum":ordernum,
					"quantity":quantity,
					"buyprice":buyprice}),
				dataType: 'text',
		        success: function (data) {
					console.log("성공");
					location.reload();
		        },
		        error: function () {
		            alert('오류가 발생했습니다.');
		        }
		    });
		});
	});
	/* 수량증가 */
	document.querySelectorAll(".plusBtn").forEach(function (btn) {
		btn.addEventListener("click", function (event) {
			let mpage = event.target.closest(".mpage");
            let quantityInput = mpage.querySelector(".quantityinput");
            let quantity = parseInt(mpage.querySelector(".quantityinput").value);
            let buyprice = mpage.querySelector(".buyprice").textContent;
			const price = mpage.querySelector('.price').textContent.replace(/[^0-9]/g, '');
			const ordernum = mpage.getAttribute('data-order-num');

			quantity++;
			quantityInput.value = quantity;
			buyprice = parseInt(price) * quantity;
			console.log("buyprice:",buyprice)
			$.ajax({
		        type: 'Post',
		        url: '/order/quantitymodify',
				contentType: "application/json",
		        data: JSON.stringify(
					{"ordernum":ordernum,
					"quantity":quantity,
					"buyprice":buyprice}),
				dataType: 'text',
		        success: function (data) {
					console.log("성공");
					location.reload();
		        },
		        error: function () {
		            alert('오류가 발생했습니다.');
		        }
		    });
		});
	});
});

/* 주문 */
/* select박스에 따른 권한변경 */
function Order() {
 /*   const order = document.querySelector('.imgdivbutton');*/
	
	const products = [];
	
	/* 체크박스 선택여부 */
	const checkboxes = document.querySelectorAll('.listcheckbox:checked');
    if (checkboxes.length === 0) {
        actionwarningalert('수정하고자 하는 상품을 선택해주세요');
		return;
	}
	/* 체크박스와 제일 근접한 상위클래스에서 추출하고자 하는 클래스 확인 */
	checkboxes.forEach(checkbox => {
        const mpage = checkbox.closest('.mpage');
		if (mpage) {
            const ordernum = mpage.getAttribute('data-order-num');
            if (ordernum) {
                products.push({
                    ordernum: ordernum,
                });
            } else {
                console.error('ordernum 요소를 찾을 수 없습니다.');
            }
        } else {
            console.error('mpage 요소를 찾을 수 없습니다.');
        }
	})
    $.ajax({
        type: 'Post',
        url: '/order/OrderCompleted',
		contentType: "application/json",
        data: JSON.stringify(products),
		dataType: 'text',
        success: function (data) {
			window.location.href = "/order/payment";
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
};