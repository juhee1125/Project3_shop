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
/* 옵션정보 불러올 때 첫번째 요소만 +버튼 */
document.addEventListener('DOMContentLoaded', function() {
    var optionGroups = document.querySelectorAll('.option-group');
    
    optionGroups.forEach((group, index) => {
        var plusButton = group.querySelector('.plus');
        if (index === 0) {
            plusButton.src = '/resources/img/icon/플러스.png';
            plusButton.onclick = addOption;
        } else {
            plusButton.src = '/resources/img/icon/마이너스.png';
            plusButton.onclick = function() {
		        document.getElementById('options-container').removeChild(group);
		    };
        }
    });
});

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
$(document).on('click', '.close-button', function() {
    // 클릭된 버튼의 이전 형제 요소인 img를 찾아 제거
    $(this).prev('img').remove();
    // 클릭된 버튼도 제거
    $(this).remove();
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
                const previewImg = document.getElementById('previewdetail');
				previewImg.innerHTML = '';
                
				const img = document.createElement('img');
	            img.src = e.target.result;
	            img.style.width = '1238px';
	            img.style.objectfit = 'cover';
	            img.style.position = 'relative';
	            img.style.left = '1.6em';
	            img.style.marginBottom = '2em';

            	const closeButton = document.createElement('button');
                closeButton.classList.add('close-detailbutton');
                closeButton.style.cssText = 'background: none; border: none; position: absolute; top: 8em; right: 3em;';

                const imgElement = document.createElement('img');
                imgElement.src = "/resources/img/icon/엑스.png"; 
                imgElement.style.height = '40px'; 
                closeButton.appendChild(imgElement);

                // X 버튼을 이미지 미리보기 컨테이너에 추가
				previewImg.appendChild(img);
                previewImg.appendChild(closeButton);

                // 클릭 이벤트 리스너 추가하여 이미지와 X 버튼 제거
                closeButton.addEventListener('click', function() {
                    previewImg.removeChild(img);
                    closeButton.parentNode.removeChild(closeButton); // X 버튼 제거
                });
            };
            reader.readAsDataURL(file);
		}
    });
});
$(document).on('click', '.close-detailbutton', function() {
    // 클릭된 버튼의 이전 형제 요소인 img를 찾아 제거
    $(this).prev('img').remove();
    // 클릭된 버튼도 제거
    $(this).remove();
});

/* 상품수정 */
$(document).ready(function() {
	$('.productbutton').click(function() {
		var formData = new FormData();
		
		var nameinput = $('.nameinput').val();
		var priceinput = $('.priceinput').val();
		var quantityinput = $('.quantityinput').val();
		var percentinput = $('.percentinput').val();
		var dateinputstart = $('#dateinputstart').val();
		var dateinputend = $('#dateinputend').val();
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
		var categoryradio = $('input[name="categoryradio"]:checked').val();

		var uploadForm = $('#uploadForm')[0];
        $(uploadForm).find('input[type="file"]').each(function() {
            var files = $(this)[0].files;
            for (var i = 0; i < files.length; i++) {
                formData.append('imageFiles', files[i]);
            }
        });
        if (formData.getAll('imageFiles').length === 0) {
            $('#previewImg .mainimagestyle').each(function() {
                formData.append('stringImageFiles', $(this).attr('src').replace('/admin/', ''));
            });
        }
		
		var existingImagePath = $('#previewdetailImg').attr('src');
        var formDetailDataObj = existingImagePath ? existingImagePath.replace('/admin/', '') : '';
        var formdetailForm = $('#uploaddetailForm')[0];
        var formdetailData = new FormData(formdetailForm);
        for (const [key, value] of formdetailData.entries()) {
            if (value instanceof File && value.size > 0) {
                formData.append('detailImageFile', value);
            }
			else {
				formData.append('stringdetailImageFile', formDetailDataObj);
			}
        }

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
		
	    $.ajax({
	        type: 'POST',
	        url: '/admin/productupdateprocess',
			data: formData,
            contentType: false,
            processData: false,
	        success: function (data) {
				if (data.message === "상품을 수정하였습니다"){
					uploadsuccessalert(data.message)
					console.log("업데이트 성공")
				}
	        },
	        error: function (xhr, status, error) {
		        console.log("업데이트 실패");
		        console.error("Error details: ", xhr, status, error);
		    }
	    });
	});
});
function uploadsuccessalert(message) {	
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
		location.href='/admin/productList';
	})
};