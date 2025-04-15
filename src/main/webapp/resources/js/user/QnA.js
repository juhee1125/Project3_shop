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
	        url: "/qna/qnainquirysearch",
	        data: { 'date': button.textContent },
			contentType: "application/json",
			dataType: 'JSON',
	        success: function (data) {
	            console.log("Ajax 성공", data); 

				const container = document.querySelector('#orderContainer');
	            container.innerHTML = '';

				data.qnalist.forEach((vo, index) => {	
	                const mpage = document.createElement('div');
	                mpage.classList.add('mpage'); 
	
	                const div1 = document.createElement('div');
	                div1.style.display = 'flex'; 
	                div1.style.alignItems = 'center'; 
	                div1.style.width = '100%'; 

					const qtitle = document.createElement('div');
					qtitle.classList.add('qtitle');
	                const div2 = document.createElement('div');
	                div2.textContent = vo.q_title; 
					qtitle.append(div2);
					
					const img1 = document.createElement('img');
					img1.classList.add('oimage');
					img1.src="/admin/" + data.pathlist[index];
					
					div1.append(img1);
					div1.append(qtitle);
					
					const qcontent = document.createElement('div');
	                qcontent.classList.add('qcontent');
					const div3 = document.createElement('div');
	                div3.textContent = vo.q_content; 
					qcontent.append(div3);
					
					const qanswerstatus = document.createElement('div');
	                qanswerstatus.classList.add('qanswerstatus');
					const div4 = document.createElement('div');
	                div4.textContent = vo.q_answerstatus; 
					qanswerstatus.append(div4);
					

	                mpage.appendChild(div1);
	                mpage.appendChild(qcontent);
	                mpage.appendChild(qanswerstatus);

					container.appendChild(mpage);
	            });
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
		
		console.log("직접입력: ",startDateInput.value, endDateInput.value)
		$.ajax({
	        type: 'GET',
	        url: "/qna/directqnainquirysearch",
	        data: { 
				'startDateInput': startDateInput.value,
			 	'endDateInput': endDateInput.value
			},
			contentType: "application/json",
			dataType: 'JSON',
	        success: function (data) {
	            console.log("Ajax 성공", data.pathlist);
	            console.log("Ajax 성공", data.qnalist);
				
				const container = document.querySelector('#orderContainer');
	            container.innerHTML = '';

				data.qnalist.forEach((vo, index) => {	
	                const mpage = document.createElement('div');
	                mpage.classList.add('mpage'); 
	
	                const div1 = document.createElement('div');
	                div1.style.display = 'flex'; 
	                div1.style.alignItems = 'center'; 
	                div1.style.width = '100%'; 

					const qtitle = document.createElement('div');
					qtitle.classList.add('qtitle');
	                const div2 = document.createElement('div');
	                div2.textContent = vo.q_title; 
					qtitle.append(div2);
					
					const img1 = document.createElement('img');
					img1.classList.add('oimage');
					img1.src="/admin/" + data.pathlist[index];
					
					div1.append(img1);
					div1.append(qtitle);
					
					const qcontent = document.createElement('div');
	                qcontent.classList.add('qcontent');
					const div3 = document.createElement('div');
	                div3.textContent = vo.q_content; 
					qcontent.append(div3);
					
					const qanswerstatus = document.createElement('div');
	                qanswerstatus.classList.add('qanswerstatus');
					const div4 = document.createElement('div');
	                div4.textContent = vo.q_answerstatus; 
					qanswerstatus.append(div4);
					

	                mpage.appendChild(div1);
	                mpage.appendChild(qcontent);
	                mpage.appendChild(qanswerstatus);

					container.appendChild(mpage);
	            });
	        },
	        error: function (error) {
	            console.error("Ajax 오류 발생", error);
	        }
	    });
    });
});