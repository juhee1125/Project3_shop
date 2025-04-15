function Reviewpopupopen(index, num){
	console.log(index);
	//상품명
	const productnamelabel = document.getElementById(`name-${index}`).textContent;
	const productoptionlabel = document.getElementById(`option-${index}`);
	const productdetaillabel = document.getElementById(`detail-${index}`);

	var QnApopupdetail = document.createElement('div');
	QnApopupdetail.style.display = 'block';
	QnApopupdetail.style.top = '400px';
	QnApopupdetail.style.width = '600px';
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
	QnApopupdetaildiv.style.marginBottom = '10px';
	//O&A작성
	var QnApopuplabel = document.createElement('label');
	QnApopuplabel.textContent = "상품 리뷰";
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
        var Reviewpopup = document.querySelector('.Reviewpopup');
        if (Reviewpopup) {
            Reviewpopup.style.display = 'none';
        }
    };
	QnApopupdetail.appendChild(QnApopupdetaildiv);
	//상품명
	var QnApopupproductlabel = document.createElement('label');
	QnApopupproductlabel.textContent = productnamelabel;
	QnApopupproductlabel.style.fontFamily = 'noto';
	QnApopupproductlabel.style.color = '#787878';
	QnApopupproductlabel.style.position = 'relative';
	QnApopupdetail.appendChild(QnApopupproductlabel);
	//상품옵션
	var productoptiondiv = document.createElement('div');
	productoptiondiv.style.position = 'relative';
	if (productoptionlabel !== null){
		var QnApopupproductoptionlabel = document.createElement('label');
		QnApopupproductoptionlabel.textContent = productoptionlabel.textContent;
		QnApopupproductoptionlabel.style.color = '#787878';
		productoptiondiv.appendChild(QnApopupproductoptionlabel);
		var QnApopupproductdetaillabel = document.createElement('label');
		QnApopupproductdetaillabel.textContent = productdetaillabel.textContent;
		QnApopupproductdetaillabel.style.color = '#787878';
		productoptiondiv.appendChild(QnApopupproductdetaillabel);
		QnApopupdetail.appendChild(productoptiondiv);
	}
	//별점
	let countstar = 0;
	var countstardiv = document.createElement('div');
	countstardiv.style.position='relative';
	countstardiv.style.margin='10px 0px';
	for (let i = 0; i < 5; i++) {
	    let starimg = document.createElement('img');
	    starimg.className = "starimg";
		starimg.id = "starimg-"+i;
	    starimg.src = "/resources/img/icon/별.png";
	    countstardiv.appendChild(starimg);

		starimg.onclick = function() {
			countstar = i+1;
			for (let j = 0; j < 5; j++) {
	            let starimg = document.getElementById("starimg-" + j);
	            if (j <= i) {
	                starimg.src = "/resources/img/icon/채운별.png";
	            } else {
	                starimg.src = "/resources/img/icon/별.png";
	            }
	        }
	    };
	}
	QnApopupdetail.appendChild(countstardiv);
	//문의내용
	var QnAcontentBox = document.createElement('textarea');
	QnAcontentBox.style.width = '595px';
	QnAcontentBox.style.height = '200px';
	QnAcontentBox.style.fontFamily = 'noto-reg';
	QnAcontentBox.style.fontSize = '14px';
	QnAcontentBox.style.resize = 'none';
	QnAcontentBox.style.color = '#171717';
	QnAcontentBox.style.position = 'relative';
	QnAcontentBox.placeholder = '여기에 상품리뷰를 적어주세요';
	QnAcontentBox.setAttribute('required', 'required');
	QnApopupdetail.appendChild(QnAcontentBox);
	//상품이미지
	let formData = new FormData();
	var reviewbuttondiv = document.createElement('div');
	reviewbuttondiv.style.display = 'flex';
	reviewbuttondiv.style.justifyContent = 'space-between';
	reviewbuttondiv.style.position = 'relative';
	for (let i = 0; i < 5; i++) {
	    let reviewimgbutton = document.createElement('button');
	    reviewimgbutton.style.width = '100px';
	    reviewimgbutton.style.height = '100px';
	    reviewimgbutton.style.border = 'none';
	    reviewimgbutton.style.position = 'relative';
	    reviewimgbutton.style.overflow = 'hidden';
	    reviewimgbutton.style.cursor = 'pointer';
	
	    // 개별 input[type="file"] 생성
	    let reviewimginput = document.createElement('input');
	    reviewimginput.type = 'file';
	    reviewimginput.accept = 'image/*';
	    reviewimginput.style.display = 'none';
	
	    // 버튼 클릭 시 input[type="file"] 실행
	    reviewimgbutton.addEventListener("click", function() {
	        reviewimginput.click();
	    });
	
	    // 이미지 태그 생성 (초기에는 안 보임)
	    let reviewimg = document.createElement('img');
	    reviewimg.style.width = '100%';
	    reviewimg.style.height = '100%';
	    reviewimg.style.position = 'absolute';
	    reviewimg.style.top = '0';
	    reviewimg.style.left = '0';
	    reviewimg.style.display = 'none'; // 처음에는 숨김
	
	    // 파일 선택 시 해당 버튼에 이미지 표시
	    reviewimginput.addEventListener("change", function(event) {
	        let file = event.target.files[0];
			formData.append('file', file);
	        if (file) {
	            let reader = new FileReader();
	            reader.onload = function(e) {
	                reviewimg.src = e.target.result;
	                reviewimg.style.display = 'block'; // 이미지 보이게 설정

	                const closeButton = document.createElement('button');
	                closeButton.classList.add('close-button');
	                closeButton.style.background = 'none';
	                closeButton.style.border = 'none';
	                closeButton.style.position = 'absolute';
	                closeButton.style.bottom = '5em';
	                closeButton.style.left = '4.8em'; 
					closeButton.style.zIndex = '1';
	
	                const imgElement = document.createElement('img');
	                imgElement.src = "/resources/img/icon/엑스버튼.png"; 
	                imgElement.style.height = '30px'; 
					imgElement.style.backgroundColor = 'gainsboro';
	                closeButton.appendChild(imgElement);
	
	                // X 버튼을 이미지 미리보기 컨테이너에 추가
	                reviewimgbutton.appendChild(closeButton);
	
	                // 클릭 이벤트 리스너 추가하여 이미지와 X 버튼 제거
	                closeButton.addEventListener('click', function(event) {
					    event.stopPropagation(); // 클릭 이벤트 전파 방지
					    reviewimg.style.display = 'none'; // 이미지 숨기기
					    reviewimg.src = ''; // 이미지 리셋
					    reviewimginput.value = ''; // input 값 초기화
					    reviewimgbutton.removeChild(closeButton); // X 버튼 제거
					});
	            };
	            reader.readAsDataURL(file);
	        }
	    });
	    // 버튼 내부에 이미지 추가
	    reviewimgbutton.appendChild(reviewimg);
	    reviewbuttondiv.appendChild(reviewimgbutton);
	    reviewbuttondiv.appendChild(reviewimginput); // input 추가 (숨김)
	}
	QnApopupdetail.appendChild(reviewbuttondiv);
	//등록버튼
	var QnApopupbutton = document.createElement('button');
	QnApopupbutton.textContent = "등록";
	QnApopupbutton.style.backgroundColor = '#f8cfbd';
	QnApopupbutton.style.border = 'none';
	QnApopupbutton.style.fontFamily = 'noto';
	QnApopupbutton.style.fontSize = '16px';
	QnApopupbutton.style.padding = '8px 55px';
	QnApopupbutton.style.position = 'relative';
	QnApopupbutton.style.left = '15em';
	QnApopupbutton.style.marginTop = '10px';
	QnApopupbutton.style.cursor = 'pointer';
	QnApopupbutton.onclick = function() {
		formData.append('onum',num);
		formData.append('countstar',countstar);
		formData.append('QnAcontentBox',QnAcontentBox.value.replace(/\s+/g, ' ').trim());
		formData.append('productnamelabel',productnamelabel);
		if (productoptionlabel !== null){
			formData.append('productoption',productoptionlabel.textContent);
			formData.append('productdetail',productdetaillabel.textContent);
		}
		else {
			formData.append('productoption',null);
			formData.append('productdetail',null);
		}		
        $.ajax({
	        type: 'POST',
	        url: "/review/reviewprocess",
	        data: formData,
			contentType: false,
            processData: false,
	        success: function (data) {
				location.reload();
	        }
	    });
    };
	QnApopupdetail.appendChild(QnApopupbutton);
	
	//팝업창
	var Reviewpopup = document.querySelector('.Reviewpopup');
	Reviewpopup.style.display = 'block';

	Reviewpopup.appendChild(QnApopupdetail);
}

function reviewsuccess(message) {	
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    })
};