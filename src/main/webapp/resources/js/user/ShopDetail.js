var optionQuantities = {};

function clickoption(select) {
    var selectoption = select.value;
	const totalprice = document.querySelector('.totalpricelabel');
	productlabel = $('.options-container').closest('.option-group').find('.option-detailgroup').text();
	console.log(productlabel);
	
    // 기본 옵션(상품을 선택해주세요)이 선택된 경우 아무 작업도 하지 않음
    if (!selectoption || selectoption === '상품을 선택해주세요') {
        return;
    }

    // 이미 생성된 옵션이 있는지 확인
    if (optionQuantities[selectoption]) {
        // 이미 존재하면 수량만 증가
        var existingInput = optionQuantities[selectoption].querySelector('input');
        existingInput.value = parseInt(existingInput.value) + 1;
		var parentdetailDiv = this.closest('.option-detailgroup');
		var pricelabel = parentdetailDiv.querySelector('label');
		var currentprice = parseInt(pricelabel.value);
		console.log(currentprice)
    } else {
        // 새로운 옵션 div 생성
        var newDiv = document.createElement('div');
        newDiv.classList.add('option-group');
        
        // 옵션명
        var optionlabel = document.createElement('label');
        optionlabel.textContent = selectoption;
        newDiv.appendChild(optionlabel);
        
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
		var pricelabel = document.getElementsByClassName('price')[0];
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

		// 총액
		let pricelabeloption = parseInt(pricelabel.textContent.replace(/[^0-9]/g, ''));
		let totalpriceoption = parseInt(totalprice.textContent.replace(/[^0-9]/g, ''));
		totalpriceoption += pricelabeloption;
		totalprice.textContent = totalpriceoption.toLocaleString() + '원';
    }
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
    
    plusBtn.onclick = function() {
        quantityinput.value++;
    };
    minusBtn.onclick = function() {
        quantityinput.value--;
        if (quantityinput.value == 0) {
            document.getElementById('options-container').removeChild(newDiv);
            delete optionQuantities[selectoption];
        }
    };
    return buttonDiv;
}

document.addEventListener('DOMContentLoaded', function() {
    const minusBtn = document.querySelector('.minusBtn');
    const plusBtn = document.querySelector('.plusBtn');
    const quantityInput = document.querySelector('.quantityinput');
	const totalprice = document.querySelector('.totalpricelabel');
	const originalprice = document.querySelector('.price');

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
		totalpriceplus += totalpriceplus;
		totalprice.textContent = totalpriceplus.toLocaleString() + '원';
    });
});