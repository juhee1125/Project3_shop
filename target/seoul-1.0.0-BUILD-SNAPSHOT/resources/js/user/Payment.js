$(document).ready(function() {
	let selectedOption = $('.couponselect')[0].options[$('.couponselect')[0].selectedIndex];
    let discount = parseFloat(selectedOption.dataset.couponDiscount);
    let price = parseFloat(selectedOption.dataset.couponPrice);
    const productprice = parseFloat(document.querySelector(".productprice").textContent.replace('원', '').replace(/,/g, ''));

    // 첫 번째 옵션 값으로 계산하기
    if (!isNaN(discount)) {
        document.querySelector(".couponapply").textContent = '-' + (productprice * (discount / 100)).toLocaleString('ko-KR') + '원';
        document.querySelector(".totalprice").textContent = (productprice * (1 - discount / 100)).toLocaleString('ko-KR') + '원';
    } else {
        document.querySelector(".couponapply").textContent = '-' + price.toLocaleString('ko-KR') + '원';
        document.querySelector(".totalprice").textContent = (productprice - price).toLocaleString('ko-KR') + '원';
    }

	$('.couponselect').change(function() {
		let selectedOption = this.options[this.selectedIndex]; 
	    let discount = parseFloat(selectedOption.dataset.couponDiscount);
	    let price = parseFloat(selectedOption.dataset.couponPrice);
		const productprice = parseFloat(document.querySelector(".productprice").textContent.replace('원', '').replace(/,/g, ''))

		if(!isNaN(discount)){
			document.querySelector(".couponapply").textContent = '-' + (productprice * (discount / 100)).toLocaleString('ko-KR') + '원';
			document.querySelector(".totalprice").textContent = (productprice * (1 - discount / 100)).toLocaleString('ko-KR') + '원';
		}
		else if(selectedOption.textContent == '쿠폰선택 안함'){
			document.querySelector(".couponapply").textContent = 0 + '원';
			document.querySelector(".totalprice").textContent = document.querySelector(".productprice").textContent;
		}
		else{
			document.querySelector(".couponapply").textContent = '-' + price.toLocaleString('ko-KR') + '원';
			document.querySelector(".totalprice").textContent = (productprice -price).toLocaleString('ko-KR') + '원';
		}
	});
});

$(document).ready(function() {
    $('.imgdivbutton').click(function() {
		let cnum = document.querySelector(".couponselect").value;
		console.log("cnum: ",document.querySelector(".couponselect").value);
		const onumlist = Array.from(document.querySelectorAll(".mpage")).map(mpage => mpage.getAttribute('data-order-num'));
		$.ajax({
	        type: 'Post',
	        url: '/order/PaymentCompleted',
			contentType: "application/json",
	        data: JSON.stringify({
				"cnum": cnum, 
            	"onumlist": onumlist
			}),
			dataType: "text",
	        success: function (data) {
				console.log(data);
				actionsuccessalert(data)
	        },
	        error: function (xhr, status, error) {
				console.error("AJAX Error! Status:", xhr.status, "Response:", xhr.responseText);
		        console.error("AJAX Error: ", xhr.responseText);
		        alert('오류가 발생했습니다: ' + xhr.responseText);
		    }
	    });
    });
});
function actionsuccessalert(message) {
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
		window.location.href = "/";
	})
};