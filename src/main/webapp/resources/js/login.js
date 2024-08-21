/* 로그인 버튼을 눌렀을 때 */
$(document).ready(function() {
	$('.loginB').click(function(e) {
		e.preventDefault();
		var userID = $('.userID').val();
	    var userPW = $('.userPW').val();	
		
		const datalist ={"userID": userID,
        "userPW": userPW}
		
		$.ajax({
	        type: 'POST',
	        url: '/login/loginprocess',
			contentType: "application/json",
	        data: JSON.stringify(datalist),
			dataType: 'text',
	        success: function (data, textStatus, xhr) {
	            console.log(data);
	            if (xhr.status === 200) {
	                console.log("로그인 성공");
					window.location.href = "/main";
	            }
	        },
	        error: function (xhr, textStatus, errorThrown) {
				console.log(textStatus)
				console.log(errorThrown);
				console.log("로그인 실패");
                loginerror("아이디 또는 비번이 일치하지 않습니다");
	        }
	    });
	})
});
function loginerror(message) {
	console.log("로그인 js로 넘어옴");
    Swal.fire({
        text: message,
        icon: 'error',
        confirmButtonText: 'OK'
    })
}

/* 아이디 저장하기 */
function IDremember() {
    var rememberCheck = document.getElementById("remember-check").checked;
    var userID = $('.userID').val();

    if (rememberCheck) {
        var expireDays = 30;
        var date = new Date();
        date.setTime(date.getTime() + expireDays * 24 * 60 * 60 * 1000);
        document.cookie = "userID=" + userID + "; expires=" + date.toUTCString() + "; path=/";
    }
}
function setUserIDFromCookie() {
    var cookies = document.cookie.split(";");
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.startsWith("userID=")) {
            var userID = cookie.substring("userID=".length);
            $('.userID').val(userID);
            break;
        }
    }
}
$(document).ready(function() {
    setUserIDFromCookie();
});