/* 비밀번호 버튼 클릭 시 작동 */
$(document).ready(function() {
	$('.findPWB').click(function() {
		var userID = $('.userID').val();
		var userName = $('.userName').val();
	    var userPhone = $('.userPhone').val();

		const datalist ={"userID": userID,
				"userName": userName,
	            "userPhone": userPhone
				}
		console.log("userID",userID,"userName",userName,"userPhone",userPhone)
		
	    $.ajax({
	        type: 'POST',
	        url: '/login/findPWprocess',
			contentType: "application/json",
	        data: JSON.stringify(datalist),
			dataType: 'text',
	        success: function (data) {
	            console.log(data);
	            if (data === "ok") {
                    location.href='/login/PWchange'
	            }
				else if (data === "이름이 일치하지 않습니다") {
					findPWerroralert(data);
				}
				else if (data === "핸드폰번호가 일치하지 않습니다") {
					findPWerroralert(data);
				}
	        },
	        error: function () {
				findPWerroralert("아이디가 일치하지 않습니다");
	        }
	    });
	});
});
function findPWsuccessalert(message) {
	console.log("비밀번호 찾기");
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
		location.href='/login/PWchange';
	})
};
function findPWerroralert(message) {
	console.log("비밀번호 찾기");
    Swal.fire({
        text: message,
        icon: 'error',
        confirmButtonText: 'OK'
    })
};
$(document).ready(function() {
	$('.findIDB').click(function() {
		window.location.href = "/login/findID";
	});
});
