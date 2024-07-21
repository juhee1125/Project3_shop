$(document).ready(function() {
	$('.findIDB').click(function() {
		var userName = $('.userName').val();
	    var userPhone = $('.userPhone').val();

		const datalist ={
				"userName": userName,
	            "userPhone": userPhone
				}
		
	    $.ajax({
	        type: 'POST',
	        url: '/login/findIDprocess',
			contentType: "application/json",
	        data: JSON.stringify(datalist),
			dataType: 'text',
	        success: function (data) {
	            console.log(data);
                $("#findIDLabel").html("회원님의 아이디는 <span id='dataText'>" + data + "</span> 입니다");
				$("#dataText").css("font-family", "noto");
				/*else if (data === "이름이 일치하지 않습니다") {
					findPWerroralert(data);
				}
				else if (data === "핸드폰번호가 일치하지 않습니다") {
					findPWerroralert(data);
				}*/
	        },
	        error: function () {
				findPWerroralert("일치하는 회원정보가 없습니다");
	        }
	    });
	});
});
$(document).ready(function() {
	$('.loginB').click(function() {
		window.location.href = "/login/login";
	});
});
$(document).ready(function() {
	$('.findPWB').click(function() {
		window.location.href = "/login/findPW";
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
