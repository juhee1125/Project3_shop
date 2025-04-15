function paymentwaitcount(users) {
	console.log(users)
    $.ajax({
        type: 'Post',
        url: '/admin/paymentwaitcount',
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
function paymentcompletcount(users) {
	console.log(users)
    $.ajax({
        type: 'Post',
        url: '/admin/paymentcompletcount',
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
function preparedeliverycount(users) {
	console.log(users)
    $.ajax({
        type: 'Post',
        url: '/admin/preparedeliverycount',
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
function deliverycount(users) {
	console.log(users)
    $.ajax({
        type: 'Post',
        url: '/admin/deliverycount',
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
function deliverycompletcount(users) {
	console.log(users)
    $.ajax({
        type: 'Post',
        url: '/admin/deliverycompletcount',
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

function search() {
    var topic = $("#searchTopic").val();
    var keyword = $("#searchKeyword").val();

    $.ajax({
        type: 'GET',
        url: "/admin/deliverysearch",
        data: { 
			'topic': topic,
			'keyword': keyword
		},
        success: function (data) {
			console.log("검색와료");
            location.reload();
        },
        error: function (error) {
            console.error("오류발생");
        }
    });
}

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
            const numElement = mpage.querySelector('.num');
            if (numElement) {
                const num = numElement.innerText;

                products.push({
					num: num
                });
            } else {
                console.error('productname 또는 productemail 요소를 찾을 수 없습니다.');
            }
        } else {
            console.error('mpage 요소를 찾을 수 없습니다.');
        }
	})
	/* 결제대기 */
	if (action === "paymentwaitcount") {
        paymentwaitcount(products);
    }
	/* 결제완료 */
	if (action === "paymentcompletcount") {
        paymentcompletcount(products);
    }
	/* 배송준비중 */
	if (action === "preparedeliverycount") {
        preparedeliverycount(products);
    }
	/* 배송중 */
	if (action === "deliverycount") {
        deliverycount(products);
    }
	/* 배송완료 */
	if (action === "deliverycompletcount") {
        deliverycompletcount(products);
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

//상품명 클릭 시 수정페이지
function clickname(num) {
	console.log(num)
    $.ajax({
        type: 'GET',
        url: "/admin/productupdate",
        data: { 
			'num': num
		},
        success: function (data) {
			console.log("상품명 클릭");
			window.location.href = "/admin/productupdate?num=" + encodeURIComponent(num);
        },
        error: function (error) {
            console.error("오류발생");
        }
    });
}