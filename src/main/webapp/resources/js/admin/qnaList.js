function search() {
    var topic = $("#searchTopic").val();
    var keyword = $("#searchKeyword").val();

    $.ajax({
        type: 'GET',
        url: "/admin/qnasearch",
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
		actionwarningalert('수정하고자 하는 역할을 선택해주세요');
	}
	 const users = [];
	
	/* 체크박스 선택여부 */
	const checkboxes = document.querySelectorAll('.listcheckbox:checked');
        if (checkboxes.length === 0) {
            actionwarningalert('수정하고자 하는 항목을 선택해주세요');
    	}
	/* 체크박스와 제일 근접한 상위클래스에서 추출하고자 하는 클래스 확인 */
	checkboxes.forEach(checkbox => {
        const mpage = checkbox.closest('.mpage');
		if (mpage) {
			const usernameElement = mpage.querySelector('.name');
			const userIDElement = mpage.querySelector('.id');
			const userPWElement = mpage.querySelector('.pw');
			const userphoneElement = mpage.querySelector('.phone');
			const roleElement =mpage.querySelector('.role');
			if (usernameElement && userIDElement && userPWElement && userphoneElement && roleElement) {
				const username = usernameElement.innerText;
				const userID = userIDElement.innerText;
				const userPW = userPWElement.innerText;
				const userphone = userphoneElement.innerText;
				const role =roleElement.innerText;
				users.push({
		            username: username,
		            userID: userID,
		            userPW: userPW,
		            userphone: userphone,
					role: role
		        });
			} else {
                console.error('productname 또는 productemail 요소를 찾을 수 없습니다.');
            }
        } else {
            console.error('mpage 요소를 찾을 수 없습니다.');
        }
	})
	const allRoles = users.map(user => user.role);
	const allAreAdmins = allRoles.every(role => role === "관리자");
	const allAreMembers = allRoles.every(role => role === "일반회원");
	/* 관리자로 승급 */
	if (action === "grantAdmin") {
		if (!allAreAdmins) {
			upgradeToAdmin(users);
		}
		else {
            actionwarningalert('선택된 항목 중에 이미 관리자가 있습니다.');
        }
	}
	/* 일반회원으로 강등 */
	else if (action === "revokeAdmin") {
        if (!allAreMembers) {
            deleteToAdmin(users);
        } else {
            actionwarningalert('선택된 항목 중에 이미 일반회원이 있습니다.');
        }
    }
	/* 회원탈퇴 */
	else {
		deleteToUser(users);
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
    $.ajax({
        type: 'GET',
        url: "/admin/QnAUpdate",
        data: { 
			'num': num
		},
        success: function (data) {
			console.log("상품명 클릭");
			window.location.href = "/admin/QnAUpdate?num=" + encodeURIComponent(num);
        },
        error: function (error) {
            console.error("오류발생");
        }
    });
}