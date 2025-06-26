$(document).ready(function() {
	let selectedOption = $('.couponselect')[0].options[$('.couponselect')[0].selectedIndex];
	//쿠폰타입(고객,상품)
	let type = selectedOption.dataset.couponType;
	//쿠폰할인방식(정액,정률)
	let setting = selectedOption.dataset.couponSetting;
	//정률할인 할인율
    let discount = parseFloat(selectedOption.dataset.couponDiscount);
	//정액할인 할인가
	let price = parseFloat(selectedOption.dataset.couponPrice);
	//상품번호
	let couponPnum = selectedOption.dataset.couponPnum;
	const onumlist = Array.from(document.querySelectorAll(".mpage")).map(mpage => mpage.getAttribute('data-product-num'));
	
    const productprice = parseFloat(document.querySelector(".productprice").textContent.replace('원', '').replace(/,/g, ''));

    // 첫 번째 옵션 값으로 계산하기
    if (type == "상품" && setting == "정률할인") {
		const matchedMpages = Array.from(document.querySelectorAll(".mpage")).filter(mpage => {
			return mpage.getAttribute('data-product-num') === couponPnum;
		});
		
		let applicableProductTotal = 0;
		console.log(matchedMpages);
		matchedMpages.forEach(mpage => {
			console.log("mpage:",mpage)
			const price = parseInt(mpage.querySelector(".buyprice").textContent.replace('원', '').replace(/,/g, ''));
			applicableProductTotal += price;
		});
		console.log("applicableProductTotal:",applicableProductTotal);
		document.querySelector(".couponapply").textContent = '-' + (applicableProductTotal * (discount / 100)).toLocaleString('ko-KR') + '원';
		document.querySelector(".totalprice").textContent = (productprice - (applicableProductTotal * (discount / 100))).toLocaleString('ko-KR') + '원';
    } else {
        document.querySelector(".couponapply").textContent = '-' + price.toLocaleString('ko-KR') + '원';
        document.querySelector(".totalprice").textContent = (productprice - price).toLocaleString('ko-KR') + '원';
    }

	$('.couponselect').change(function() {
		let selectedOption = this.options[this.selectedIndex]; 
	    let discount = parseFloat(selectedOption.dataset.couponDiscount);
	    let price = parseFloat(selectedOption.dataset.couponPrice);
		let type = selectedOption.dataset.couponType;
		let setting = selectedOption.dataset.couponSetting;
		let couponPnum = selectedOption.dataset.couponPnum;
		
		const productprice = parseFloat(document.querySelector(".productprice").textContent.replace('원', '').replace(/,/g, ''))
		console.log("productprice: ",productprice * (discount / 100))
		
		if (type == "상품" && setting == "정률할인") {
				const matchedMpages = Array.from(document.querySelectorAll(".mpage")).filter(mpage => {
					return mpage.getAttribute('data-product-num') === couponPnum;
				});
				
				let applicableProductTotal = 0;
				console.log(matchedMpages);
				matchedMpages.forEach(mpage => {
					console.log("mpage:",mpage)
					const price = parseInt(mpage.querySelector(".buyprice").textContent.replace('원', '').replace(/,/g, ''));
					applicableProductTotal += price;
				});
				console.log("applicableProductTotal:",applicableProductTotal);
				document.querySelector(".couponapply").textContent = '-' + (applicableProductTotal * (discount / 100)).toLocaleString('ko-KR') + '원';
				document.querySelector(".totalprice").textContent = (productprice - (applicableProductTotal * (discount / 100))).toLocaleString('ko-KR') + '원';
		}
		else if(selectedOption.textContent == '쿠폰선택 안함'){
			document.querySelector(".couponapply").textContent = 0 + '원';
			document.querySelector(".totalprice").textContent = document.querySelector(".productprice").textContent;
		}
		else if(type == "고객" && setting == "정률할인"){
			document.querySelector(".couponapply").textContent = '-' + (productprice * (discount / 100)).toLocaleString('ko-KR') + '원';
			document.querySelector(".totalprice").textContent = (productprice -(productprice * (discount / 100))).toLocaleString('ko-KR') + '원';
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
		let couponapplydiv = document.querySelector(".couponapply").textContent;
		console.log("couponapplydiv:",couponapplydiv)
		let couponapply = couponapplydiv.replace(/[^0-9]/g, '');
		console.log("couponapply:",couponapply)
		const onumlist = Array.from(document.querySelectorAll(".mpage")).map(mpage => mpage.getAttribute('data-order-num'));
		$.ajax({
	        type: 'Post',
	        url: '/order/PaymentCompleted',
			contentType: "application/json",
	        data: JSON.stringify({
				"cnum": cnum, 
				"couponapply": couponapply,
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