/* 옵션 */
function addOption() {
	console.log("옵션 추가 js 넘어옴")
    // 새로운 옵션 그룹을 생성
    var newOptionGroup = document.createElement('div');
    newOptionGroup.className = 'option-group';

    // 옵션명 입력란 생성
    var optionNameInput = document.createElement('input');
    optionNameInput.type = 'text';
    optionNameInput.className = 'optioninput';
    optionNameInput.placeholder = '옵션명';

    // 옵션정보 입력란 생성
    var optionDetailInput = document.createElement('input');
    optionDetailInput.type = 'text';
    optionDetailInput.className = 'optiondetailinput';
    optionDetailInput.placeholder = '옵션정보';

    var minus = document.createElement('img');
    minus.src = "/resources/img/icon/마이너스.png";
    minus.className = 'plus';

	minus.onclick = function() {
        document.getElementById('options-container').removeChild(newOptionGroup);
    };

    // 새로운 옵션 그룹에 입력란 추가
    newOptionGroup.appendChild(optionNameInput);
    newOptionGroup.appendChild(optionDetailInput);
    newOptionGroup.appendChild(minus);

    // 옵션 컨테이너에 새로운 옵션 그룹 추가
    document.getElementById('options-container').appendChild(newOptionGroup);
}

/* 메인이미지 다중 업로드 */
$(document).ready(function() {
    $('#imageFile').change(function() {
		//업로드 가능한 갯수 지정
		const maxFiles = 5;
		//선택한 이미지요소 저장한 공간
        const previewContainer = document.getElementById('previewImg');
        previewContainer.innerHTML = ''; // 기존 이미지 초기화
		const files = this.files;

		if (files.length > maxFiles) {
            uploadwarningalert(`최대 ${maxFiles}개의 파일만 선택할 수 있습니다.`);
            this.value = ''; // 선택된 파일 초기화
            return;
        }
		
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();
            reader.onload = function(e) {
                const img = document.createElement('img');
	            img.src = e.target.result;
	            img.style.width = '200px';
	            img.style.height = '200px';
	            img.style.objectFit = 'cover';
	            img.style.marginRight = '-20px';
	            img.style.marginLeft = '60px';
	
	            const closeButton = document.createElement('button');
	            closeButton.classList.add('close-button');
				closeButton.style.background = 'none';
				closeButton.style.border = 'none';					
	            const imgElement = document.createElement('img');
				imgElement.src = "/resources/img/icon/엑스.png";
				imgElement.style.height = '40px';
				imgElement.style.position = 'absolute';
				imgElement.style.bottom = '13.5em';
				closeButton.appendChild(imgElement);
				
	            closeButton.addEventListener('click', function() {
	                img.remove(); // 이미지 제거
	                closeButton.remove(); // X 버튼 제거
	            });
	
	            // 이미지와 X 버튼을 묶어주는 컨테이너 생성 없이 각각 추가
	            previewContainer.appendChild(img);
	            previewContainer.appendChild(closeButton);
            };
            reader.readAsDataURL(file); // 파일을 읽어와서 미리보기 생성
        }
    });
});
function uploadwarningalert(message) {
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    })
};

/* 상세이미지 업로드 */
$(document).ready(function() {
    $('#imagedetailFile').change(function() {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const previewImg = document.getElementById('previewdetailImg');
                previewImg.setAttribute('src', e.target.result);

                const closeButton = document.createElement('button');
                closeButton.classList.add('close-button');
                closeButton.style.background = 'none';
                closeButton.style.border = 'none';
                closeButton.style.position = 'absolute';
                closeButton.style.top = '8em';
                closeButton.style.right = '3em'; 

                const imgElement = document.createElement('img');
                imgElement.src = "/resources/img/icon/엑스.png"; 
                imgElement.style.height = '40px'; 
                closeButton.appendChild(imgElement);

                // X 버튼을 이미지 미리보기 컨테이너에 추가
                previewImg.parentNode.appendChild(closeButton);

                // 클릭 이벤트 리스너 추가하여 이미지와 X 버튼 제거
                closeButton.addEventListener('click', function() {
                    previewImg.removeAttribute('src'); // 이미지 미리보기 제거
                    closeButton.parentNode.removeChild(closeButton); // X 버튼 제거
                    $('#imagedetailFile').val(''); // 필요 시 파일 입력 값 초기화
                });
            };
            reader.readAsDataURL(file);
        }
    });
});


/* 상품등록 */
$(document).ready(function() {
    $('.productbutton').click(function() {
		var formData = new FormData();
		
		var nameinput = $('.nameinput').val();
        var priceinput = $('.priceinput').val();
        var quantityinput = $('.quantityinput').val();
        var percentinput = $('.percentinput').val();
        var dateinputstart = $('#dateinputstart').val();
        var dateinputend = $('#dateinputend').val();
		var categoryradio = $('input[name="categoryradio"]:checked').val();
		
        var options = [];
        $('.option-group').each(function() {
            var optioninput = $(this).find('.optioninput').val();
            var optiondetailinput = $(this).find('.optiondetailinput').val();
            
            if (optioninput !== "" && optiondetailinput !== "") {
                options.push({ optioninput: optioninput, optiondetailinput: optiondetailinput });
            }
        });
		var optioninputlist = [];
		var optiondetailinputlist = [];
		options.forEach(function(option) {
			optioninputlist.push(option.optioninput)
			optiondetailinputlist.push(option.optiondetailinput)
		});
        
        var uploadForm = $('#uploadForm')[0];
        var uploaddetailForm = $('#uploaddetailForm')[0];
        var imageFilesSelected = false;
        $(uploadForm).find('input[type="file"]').each(function() {
            var files = $(this)[0].files;
            for (var i = 0; i < files.length; i++) {
                formData.append('imageFiles', files[i]);
				imageFilesSelected = true;
            }
        });
        var detailImageFileSelected = false;
        $(uploaddetailForm).find('input[type="file"]').each(function() {
            var file = $(this)[0].files[0];
            if (file) {
                formData.append('detailImageFile', file);
				detailImageFileSelected = true;
            }
        });
        
        formData.append('nameinput', nameinput);
        formData.append('priceinput', priceinput);
        formData.append('quantityinput', quantityinput);
        formData.append('percentinput', percentinput);
        formData.append('dateinputstart', dateinputstart);
        formData.append('dateinputend', dateinputend);
        formData.append('categoryradio', categoryradio);
		formData.append('optioninput', JSON.stringify(optioninputlist));
        formData.append('optiondetailinput', JSON.stringify(optiondetailinputlist));

		for (const [key, value] of formData.entries()) {
		    console.log(key, value);
		}
		
		if (nameinput === null || priceinput === null || quantityinput === null || categoryradio === null || 
		!imageFilesSelected || !detailImageFileSelected) {
			uploadwarningalert("필수항목을 입력해주세요");
		}
		else if (percentinput === "" && dateinputstart !== "") {
            uploadwarningalert("할인율을 입력해주세요");
        } 
		else if (percentinput !== "" && dateinputstart === "") {
            uploadwarningalert("할인기간을 입력해주세요");
        }
		else {
            uploadcheckalert(formData);
		}
    });
});
function uploadcheckalert(formData) {
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
}
function uploadsuccessalert(message) {
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
        location.href='/admin/productList';
    });
}