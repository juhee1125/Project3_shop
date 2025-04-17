/* 라디오박스에 따른 비활성화 */
$(document).ready(function() {
	$('.couponradio').click(function() {
		/* 발행유형 */
	    if ($('input[name="coupontargetradio"]:checked').val() == "customer"){
			document.getElementById("productselect").disabled = true;
			console.log("고객")
		}
		else {
			document.getElementById("productselect").disabled = false;
			console.log("상품")
		}	
		/* 할인설정 */
		if ($('input[name="coupontyperadio"]:checked').val() == "percentradio"){
			document.querySelectorAll("#priceinput").forEach(input => {
				input.disabled = true;
			});
			document.getElementById("percentinput").disabled = false;
			console.log("정율할인")
		}
		else {
			document.querySelectorAll("#priceinput").forEach(input => {
				input.disabled = false;
			});
			document.getElementById("percentinput").disabled = true;
			console.log("정액할인")
		}
	});
});


/* 쿠폰등록 */
$(document).ready(function() {
    $('.productbutton').click(function() {	
		var formData = new FormData();
		
		var nameinput = $('.nameinput').val();
		formData.append('nameinput', nameinput);
		
	    let productname = $('#productselect').val(); ;
		if ($('input[name="coupontargetradio"]:checked').val() == "product"){
			$('#productselect').change(function() {
			    productname = $(this).val();
			});
			formData.append('productlabel', "상품");
			formData.append('productname', productname);
		}
		else{
			formData.append('productlabel', "고객");
		}
        
		var numberinput = $('.numberinput').val();
		formData.append('numberinput', numberinput);
		
		var discountlist = [];	
		if ($('input[name="coupontyperadio"]:checked').val() == "percentradio"){
			discountlist.push($('#percentinput').val());
			formData.append('discountlabel', "정률할인");
			formData.append('discountlist', JSON.stringify(discountlist));
		}
		else {			
			document.querySelectorAll("#priceinput").forEach(input => {
				discountlist.push($(input).val());
			});
			formData.append('discountlabel', "정액할인");
			formData.append('discountlist', JSON.stringify(discountlist));
		}
	
		var dateinputstart = $('#dateinputstart').val();
		var dateinputend = $('#dateinputend').val();
		formData.append('dateinputstart', dateinputstart);
		formData.append('dateinputend', dateinputend);
		
		var percentinput = $('#percentinput').val();
		var priceinput = $('#priceinput').val();

		if (nameinput === "" || dateinputstart === "" || dateinputend === "") {
			uploadwarningalert("필수항목을 입력해주세요");
		}
		else if (!$('input[name="couponnumbercheck"]').prop('checked') && numberinput === "") {
		    uploadwarningalert("필수항목을 입력해주세요");
		}
		else if (percentinput === "" && priceinput === ""){
			uploadwarningalert("필수항목을 입력해주세요");
		}
		else {
            $.ajax({
                type: 'POST',
                url: '/admin/couponuploadprocess',
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
					console.log(data);
					if (data.message==="쿠폰을 등록하였습니다"){
						uploadsuccessalert(data.message);
					}
                }
            });
		}
    });
});
/*function uploadcheckalert(formData) {
    let displayText = "";
    var data = Object.fromEntries(formData.entries());
	const discountedPrice = data.priceinput-(data.priceinput * (data.percentinput / 100));
    
    displayText += `<strong>상품명 :</strong> ${data.nameinput}<br/><br/>`;
    displayText += `<strong>가격 :</strong> ${data.priceinput}원<br/><br/>`;
    displayText += `<strong>수량 :</strong> ${data.quantityinput}<br/><br/>`;
    if (data.percentinput !== ""){
        displayText += `<strong>할인율 :</strong> ${data.percentinput}%<br/><br/>`;
    }
	displayText += `<strong>할인적용가 :</strong> ${discountedPrice}원<br/><br/>`;
    if (data.dateinputstart !== "" && data.dateinputend !== ""){
        displayText += `<strong>할인기간 :</strong> ${data.dateinputstart} ~ ${data.dateinputend}<br/><br/>`;
    }
    let optionInputs = JSON.parse(data.optioninput);
    let optionDetailInputs = JSON.parse(data.optiondetailinput);
	for (let i = 0; i < optionInputs.length; i++) {
        displayText += `<strong>옵션명 :</strong> ${optionInputs[i]}<br/>`;
        displayText += `<strong>옵션정보 :</strong> ${optionDetailInputs[i]}<br/><br/>`;
    }
    displayText += `<strong>카테고리 :</strong> ${data.categoryradio}`;
    
    Swal.fire({
        title: "상품을 등록하시겠습니까?",
        html: displayText,
        confirmButtonText: '등록',
        showCancelButton: true,
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: 'POST',
                url: '/admin/productuploadprocess',
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
					console.log(data)
					if (data.message==="상품을 등록하였습니다"){
						uploadsuccessalert(data.message);
					}
					else if(data.message==="이미 등록된 상품명입니다") {
						uploadwarningalert(data.message);
					}
                }
            });
        }
    });
}*/
function uploadwarningalert(message) {
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    })
}
function uploadsuccessalert(message) {
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
        location.href='/admin/couponList';
    });
}