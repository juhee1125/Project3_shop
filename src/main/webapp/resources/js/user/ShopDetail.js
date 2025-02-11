var optionQuantities = {};
let option=[];
function clickoption(select) {
    var selectoption = select.value;
	const totalprice = document.querySelector('.totalpricelabel');
	productlabel = $('.options-container').closest('.option-group').find('.option-detailgroup').text();
	const basicprice = document.querySelector('.price');
	var pricelabel = document.getElementsByClassName('price')[0];
	
    // 기본 옵션(상품을 선택해주세요)이 선택된 경우 아무 작업도 하지 않음
    if (!selectoption || selectoption === '상품을 선택해주세요') {
        return;
    }

    // 이미 생성된 옵션이 있는지 확인
    if (optionQuantities[selectoption]) {
        // 이미 존재하면 수량만 증가
        var existingInput = optionQuantities[selectoption].querySelector('input');
        existingInput.value = parseInt(existingInput.value) + 1;

		var existingLabel = optionQuantities[selectoption].querySelector('label');
		let pricelabelplus = parseInt(existingLabel.textContent.replace(/[^0-9]/g, ''));
		let basicpricelabelplus = parseInt(basicprice.textContent.replace(/[^0-9]/g, ''));
		let existingLabelupdate = (pricelabelplus + basicpricelabelplus);
		existingLabel.textContent = existingLabelupdate.toLocaleString() + '원';
		
    } else {
        // 새로운 옵션 div 생성
        var newDiv = document.createElement('div');
        newDiv.classList.add('option-group');
        
        // 옵션명
        var optionlabel = document.createElement('label');
		optionlabel.className = "optionlabel";
        optionlabel.textContent = selectoption;
        newDiv.appendChild(optionlabel);
		
		const selectedOption = select.selectedOptions[0];
		const po_option = selectedOption.dataset.option;
		const po_optiondetail = selectedOption.dataset.optiondetail;
		option.push({ po_option, po_optiondetail});
        
		// 수량,가격 div 생성
		var newDiv2 = document.createElement('div');
        newDiv2.classList.add('option-detailgroup');
		newDiv2.style.display = "flex";
		newDiv2.style.alignItems = "center";
		newDiv2.style.justifyContent = "space-between";

        var buttonDiv = quantityoption(newDiv, selectoption);
        newDiv2.appendChild(buttonDiv);
		
		// 옵션가격
		var optionpricelabel = document.createElement('label');
		optionpricelabel.className = "optionpricelabel";
        optionpricelabel.textContent = pricelabel.textContent;
		optionpricelabel.style.marginTop = '20px';
		optionpricelabel.style.marginLeft = 'auto';
        newDiv2.appendChild(optionpricelabel);
        
		// 엑스버튼
		var cancelbutton = document.createElement('button');
		cancelbutton.style.backgroundImage = 'url(/resources/img/icon/엑스버튼.png)';
		cancelbutton.style.width = '40px';
    	cancelbutton.style.height = '40px';
		cancelbutton.style.backgroundSize = '80%';
    	cancelbutton.style.backgroundPosition = 'center';
		cancelbutton.style.border = '0';
    	cancelbutton.style.cursor = 'pointer';
    	cancelbutton.style.backgroundColor = '#F7F8F7';
    	cancelbutton.style.marginTop = '20px';
		newDiv2.appendChild(cancelbutton);
		
		
		cancelbutton.onclick = function() {
	        var parentDiv = this.closest('.option-group');
		    document.getElementById('options-container').removeChild(parentDiv);

		    delete optionQuantities[selectoption];
			
			// 총액
			var parentdetailDiv = this.closest('.option-detailgroup');
    		var quantityinput = parentdetailDiv.querySelector('input');
    		var currentQuantity = parseInt(quantityinput.value);
			let pricelabeloption = parseInt(pricelabel.textContent.replace(/[^0-9]/g, ''));
			let totalpriceoption = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
			totalpriceoption -= (pricelabeloption*currentQuantity);
			totalprice.textContent = totalpriceoption.toLocaleString() + '원';
	    };

		newDiv.appendChild(newDiv2);	
        var optionContainer = document.getElementById('options-container');
        optionContainer.appendChild(newDiv);

        optionQuantities[selectoption] = newDiv2;

    }

	// 총액
	let pricelabeloption = parseInt(pricelabel.textContent.replace(/[^0-9]/g, ''));
	let totalpriceoption = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
	totalpriceoption += (pricelabeloption);
	totalprice.textContent = totalpriceoption.toLocaleString() + '원';
    select.selectedIndex = 0;
}

function createButton(imgUrl) {
    var button = document.createElement('button');
    button.style.width = '29px';
    button.style.height = '35px';
    button.style.border = '0';
    button.style.backgroundImage = `url(${imgUrl})`;
    button.style.backgroundSize = '80%';
    button.style.backgroundPosition = 'center';
    button.style.cursor = 'pointer';
    return button;
}

function quantityoption(newDiv, selectoption) {
    var buttonDiv = document.createElement('div');
    buttonDiv.style.marginTop = '20px';
    buttonDiv.style.maxWidth = '138px';
    buttonDiv.style.border = '1px solid #ddd';
    buttonDiv.style.overflow = 'hidden';
    buttonDiv.style.display = 'flex';
    
    var minusBtn = createButton('/resources/img/icon/마이너스2.png');
    buttonDiv.appendChild(minusBtn);
    
    var quantityinput = document.createElement('input');
	quantityinput.className = 'quantityinput';
    quantityinput.value = 1;
    quantityinput.style.width = '60px';
    quantityinput.style.height = '35px';
    quantityinput.style.padding = '0 10px';
    quantityinput.style.border = '0';
    quantityinput.style.textAlign = 'center';
    quantityinput.style.color = '#171717';
    quantityinput.style.fontSize = '17px';
    quantityinput.style.fontFamily = 'noto';
    buttonDiv.appendChild(quantityinput);
    
    var plusBtn = createButton('/resources/img/icon/플러스.png');
    buttonDiv.appendChild(plusBtn);

	quantityinput.onchange = function() {
		const basicprice = document.querySelector('.price');
		var existingLabel = optionQuantities[selectoption].querySelector('label');
		let pricelabelplus = parseInt(existingLabel.textContent.replace(/[^0-9]/g, ''));
		let basicpricelabelplus = parseInt(basicprice.textContent.replace(/[^0-9]/g, ''));
		let existingLabelupdate = (quantityinput.value*basicpricelabelplus);
		existingLabel.textContent = existingLabelupdate.toLocaleString() + '원';
		// 총액
		const totalprice = document.querySelector('.totalpricelabel');
		let totalpriceoption = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
		totalpriceoption += (basicpricelabelplus*quantityinput.value)-pricelabelplus;
		totalprice.textContent = totalpriceoption.toLocaleString() + '원';
    	select.selectedIndex = 0;
	}
    
    plusBtn.onclick = function() {
        quantityinput.value++;

		const basicprice = document.querySelector('.price');
		var existingLabel = optionQuantities[selectoption].querySelector('label');
		let pricelabelplus = parseInt(existingLabel.textContent.replace(/[^0-9]/g, ''));
		let basicpricelabelplus = parseInt(basicprice.textContent.replace(/[^0-9]/g, ''));
		let existingLabelupdate = (pricelabelplus + basicpricelabelplus);
		existingLabel.textContent = existingLabelupdate.toLocaleString() + '원';
		// 총액
		const totalprice = document.querySelector('.totalpricelabel');
		var pricelabel = document.getElementsByClassName('price')[0];
		let pricelabeloption = parseInt(pricelabel.textContent.replace(/[^0-9]/g, ''));
		let totalpriceoption = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
		totalpriceoption += (pricelabeloption);
		totalprice.textContent = totalpriceoption.toLocaleString() + '원';
    	select.selectedIndex = 0;
    };
    minusBtn.onclick = function() {
        quantityinput.value--;
        if (quantityinput.value == 0) {
			quantityinput.value=1;
        }

		const basicprice = document.querySelector('.price');
		var existingLabel = optionQuantities[selectoption].querySelector('label');
		let pricelabelplus = parseInt(existingLabel.textContent.replace(/[^0-9]/g, ''));
		let basicpricelabelplus = parseInt(basicprice.textContent.replace(/[^0-9]/g, ''));
		let existingLabelupdate = (pricelabelplus - basicpricelabelplus);
		existingLabel.textContent = existingLabelupdate.toLocaleString() + '원';
		// 총액
		const totalprice = document.querySelector('.totalpricelabel');
		var pricelabel = document.getElementsByClassName('price')[0];
		let pricelabeloption = parseInt(pricelabel.textContent.replace(/[^0-9]/g, ''));
		let totalpriceoption = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
		totalpriceoption -= (pricelabeloption);
		totalprice.textContent = totalpriceoption.toLocaleString() + '원';
    	select.selectedIndex = 0;
    };
    return buttonDiv;
}

//옵션이 없을때
document.addEventListener('DOMContentLoaded', function() {
    const minusBtn = document.querySelector('.minusBtn');
    const plusBtn = document.querySelector('.plusBtn');
    const quantityInput = document.querySelector('.quantityinput');
	const totalprice = document.querySelector('.totalpricelabel');
	const originalprice = document.querySelector('.price');
	const basicprice = document.querySelector('.price');

	minusBtn.addEventListener('click', function() {
        quantityInput.value--;
		let totalpriceminus = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
		let originalpriceminus = parseInt(originalprice.textContent.replace(/[^0-9]/g, ''));
		totalpriceminus -= originalpriceminus;
		totalprice.textContent = totalpriceminus.toLocaleString() + '원';
		if (quantityInput.value==0){
			quantityInput.value=1;
			totalprice.textContent = originalpriceminus.toLocaleString() + '원';;
		}
    });

    plusBtn.addEventListener('click', function() {
        quantityInput.value++;
		let totalpriceplus = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
		let basicpricelabelplus = parseInt(basicprice.textContent.replace(/[^0-9]/g, ''));
		totalpriceplus += basicpricelabelplus;
		totalprice.textContent = totalpriceplus.toLocaleString() + '원';
    });
});

//상세정보 탭
$(document).ready(function(){
	$('ul.tabs li').click(function(){
		var tab_id = $(this).attr('data-tab');
		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');
		$(this).addClass('current');
		$("#"+tab_id).addClass('current');
	})
});

//리뷰 별점
$(document).ready(function(){
	document.querySelectorAll('.stars').forEach(star => {
		let rating = star.getAttribute("data-rating");
		
		for (let i = 0; i < rating; i++) {
		    let img = document.createElement("img");
		    img.src =  "/resources/img/icon/채운별.png";
		    img.style.width =  "30px";
			img.style.marginRight =  "5px";
		    star.appendChild(img);
		}
		
		for (let i = 0; i < 5 - rating; i++) {
		    let img = document.createElement("img");
		    img.src = "/resources/img/icon/별.png";
			img.style.width =  "30px";
			img.style.marginRight =  "5px";
		    star.appendChild(img);
		}
	});
});
$(document).ready(function(){
	const reviewcontentlabel = document.querySelector('.reviewcontentlabel');
	reviewcontentlabel.textContent = reviewcontentlabel.replace("\\n", "\n");
});

//상품문의
$(document).ready(function() {
    $('.tabbutton').click(function() {
		const currentUrl = window.location.href;
		$.ajax({
	        type: 'POST',
	        url: "/detail/ProductQnA",
			contentType: "application/json",
	        data: JSON.stringify({"redirectUrl":currentUrl}),
	        success: function (data) {
				if (data.message==="로그인페이지로 이동"){
					window.location.href = "/login/login";
				}
				else if (data.message==="정보입력"){
					QnApopupopen();
				}
	        }
	    });
    });
});

//상품문의 클릭 시
function QnApopupopen(){
	//상품명
	const infodivlabel = document.querySelector('.infodivlabel').textContent;

	var QnApopupdetail = document.createElement('div');
	QnApopupdetail.style.display = 'block';
	QnApopupdetail.style.top = '400px';
	QnApopupdetail.style.width = '600px';
	QnApopupdetail.style.height = '480px';
	QnApopupdetail.style.zIndex = '999';
	QnApopupdetail.style.left = '50%';
	QnApopupdetail.style.margin = '-258px 0px 0px -325px';
	QnApopupdetail.style.position = 'absolute';
	QnApopupdetail.style.backgroundColor = 'white';
	QnApopupdetail.style.padding = '20px';
	//타이틀 div
	var QnApopupdetaildiv = document.createElement('div');
	QnApopupdetaildiv.style.borderBottom = '2px solid #171717';
	QnApopupdetaildiv.style.paddingBottom = '20px';
	//O&A작성
	var QnApopuplabel = document.createElement('label');
	QnApopuplabel.textContent = "상품 QnA";
	QnApopuplabel.style.fontFamily = "noto";
	QnApopuplabel.style.fontSize = "25px";
	QnApopupdetaildiv.appendChild(QnApopuplabel);
	//엑스버튼
	var QnApopupimg = document.createElement('img');
	QnApopupimg.src = "/resources/img/icon/엑스버튼.png";
	QnApopupimg.style.width = '50px';
	QnApopupimg.style.position = 'absolute';
	QnApopupimg.style.top = '10px';
	QnApopupimg.style.right = '10px';
	QnApopupimg.style.cursor = 'pointer';
	QnApopupdetaildiv.appendChild(QnApopupimg);
	QnApopupimg.onclick = function() {
        var QnApopup = document.querySelector('.QnApopup');
        if (QnApopup) {
            QnApopup.style.display = 'none';
        }
    };
	QnApopupdetail.appendChild(QnApopupdetaildiv);
	//상품명
	var QnApopupproductlabel = document.createElement('label');
	QnApopupproductlabel.textContent = infodivlabel;
	QnApopupproductlabel.style.fontFamily = 'noto';
	QnApopupproductlabel.style.color = '#787878';
	QnApopupproductlabel.style.position = 'absolute';
	QnApopupproductlabel.style.top = '100px';
	QnApopupdetail.appendChild(QnApopupproductlabel);
	//문의내용
	var QnAcontentBox = document.createElement('textarea');
	QnAcontentBox.style.width = '595px';
	QnAcontentBox.style.height = '200px';
	QnAcontentBox.style.marginTop = '75px';
	QnAcontentBox.style.fontFamily = 'noto-reg';
	QnAcontentBox.style.fontSize = '14px';
	QnAcontentBox.style.resize = 'none';
	QnAcontentBox.style.color = '#171717';
	QnAcontentBox.placeholder = '여기에 문의 내용을 적어주세요';
	QnAcontentBox.setAttribute('required', 'required');
	QnApopupdetail.appendChild(QnAcontentBox);
	//등록버튼
	var QnApopupbutton = document.createElement('button');
	QnApopupbutton.textContent = "등록";
	QnApopupbutton.style.backgroundColor = '#f8cfbd';
	QnApopupbutton.style.border = 'none';
	QnApopupbutton.style.fontFamily = 'noto';
	QnApopupbutton.style.fontSize = '16px';
	QnApopupbutton.style.padding = '8px 55px';
	QnApopupbutton.style.position = 'absolute';
	QnApopupbutton.style.left = '15em';
	QnApopupbutton.style.bottom = '6em';
	QnApopupbutton.style.cursor = 'pointer';
	QnApopupbutton.onclick = function() {
		console.log("상품명: ",infodivlabel);
		console.log("문의내용: ",QnAcontentBox.value);
        $.ajax({
	        type: 'POST',
	        url: "/detail/ProductQnAprocess",
			contentType: "application/json",
	        data: JSON.stringify({"infodivlabel":infodivlabel, "QnAcontentBox":QnAcontentBox.value}),
	        success: function (data) {
				location.reload();
	        }
	    });
    };
	QnApopupdetail.appendChild(QnApopupbutton);
	
	//팝업창
	var QnApopup = document.querySelector('.QnApopup');
	QnApopup.style.display = 'block';

	QnApopup.appendChild(QnApopupdetail);
}

//문의 내용 클릭 시
function clickqnacontent(index, answer) { 
    // 줄바꿈 문자 처리
    const decodedAnswer = answer.replace(/\\n/g, '\n');
    console.log("decodedAnswer: " + decodedAnswer);

    const answerDiv = document.getElementById(`answer-${index}`);

    if (answerDiv.style.display === 'none') {
        answerDiv.style.display = 'block'; 
        const newLabel = document.createElement('label');
        newLabel.textContent = decodedAnswer;
        answerDiv.appendChild(newLabel);
    } else {
        answerDiv.style.display = 'none';
        answerDiv.innerHTML = ''; 
    }
}


// 장바구니 클릭 시
$(document).ready(function() {
	$('#shopping').click(function() {
		const totalquantity = document.getElementsByClassName("totalquantity");
		const optionsContainer = document.getElementById("options-container");
        const optionGroup = optionsContainer.querySelector(".option-group");

		//상품명
		const infodivlabel = document.getElementsByClassName("infodivlabel");
		const productname = infodivlabel[0].textContent;
		//상품이미지
		const mainimage = document.getElementsByClassName("mainimage");
		const productimage = mainimage[0].getAttribute('src')
		//상품가격
		const price = document.getElementsByClassName("price");
		const productprice = price[0].textContent.trim().replace(/\D/g, "");
		//상품총액
		const totalpricelabel = document.getElementsByClassName("totalpricelabel");
		const producttotalprice = totalpricelabel[0].textContent.trim().replace(/\D/g, "");
		//옵션명
		const optionlabel = document.getElementsByClassName("optionlabel");
		console.log(option);
		if (optionlabel){
			optionname = Array.from(optionlabel).map(element => element.textContent.trim());
		}
		//옵션수량
		const quantityinput = document.getElementsByClassName("quantityinput");
		const optionquantity = Array.from(quantityinput).map(element => element.value);
		//옵션가격
		const optionpricelabel = document.getElementsByClassName("optionpricelabel");
		let optionnprice=[];
		if (optionpricelabel){
			optionnprice = Array.from(optionpricelabel).map(element => element.textContent.trim().replace(/\D/g, ""));
			console.log(optionnprice)
		}
		
		if(optionGroup || (totalquantity.length > 0)){
  			$.ajax({
	            type: 'POST',
				contentType: "application/json",
	            url: "/order/shoppingprocess",
	            data: JSON.stringify({
					"productname":productname,
					"productimage":productimage,
					"productprice":productprice,
					"producttotalprice":producttotalprice,
					"option":option,
					"optionquantity":optionquantity,
					"optionnprice":optionnprice
				}),
	            success: function (data) {
					if (data.message === "로그인 후 가능합니다"){
						loginfirst(data.message)
					}
					else{
						optionselect(data.message)
					}
	            }
	        });
		}
		else{
			optionnotselect("옵션을 선택해주세요")
		}
    });
});
function optionselect(message) {	
	Swal.fire({
		text: message,
   		icon: 'success',
   
   		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
   		confirmButtonColor: '#7066e0', // confrim 버튼 색깔 지정
   		cancelButtonColor: '#7d7d7d', // cancel 버튼 색깔 지정
   		confirmButtonText: '장바구니 가기',
   		cancelButtonText: '쇼핑 더 하기',
	}).then(result => {
		if (result.isConfirmed) {
  			window.location.href = "/order/shopping";
		}
	});
};
function optionnotselect(message) {	
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    })
};
function loginfirst(message) {
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    }).then(function(){
		location.href='/login/login';
	})
};