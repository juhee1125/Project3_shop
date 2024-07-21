$(document).ready(function() {
	$('.changePWB').click(function() {
	    var userPW = $('.userPW').val();
		
	    $.ajax({
	        type: 'POST',
	        url: '/login/PWchangeprocess',
			contentType: "application/json",
	        data: JSON.stringify({"userPW": userPW}),
			dataType: 'text',
	        success: function (data) {
	            console.log(data);
	            if (data === "비밀번호가 변경되었습니다" && $("#passwordLabel").text() === "안전한 아이디입니다" 
					&& $(".userPW").val()===$(".passwordcheck").val()) {
	                console.log("비밀번호 변경");
                    PWchangesuccessalert(data);
	            }
	        },
	        error: function () {
				console.log("비밀번호 변경 실패");
				if ($("#passwordLabel").text() !== "안전한 아이디입니다" || $(".userPW").val()!==$(".passwordcheck").val()){
					PWchangewarningalert("비밀번호를 확인해주세요")
				}
	        }
	    });
	});
});
function PWchangesuccessalert(message) {
	console.log("비번변경 js로 넘어옴");
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
		location.href='/login/login';
	})
};
function PWchangewarningalert(message) {
	console.log("아이디입력");
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    })
};
/* 비밀번호 표시 */
$(document).ready(function() {
	$('.noPW').click(function() {
		var PWicon = $(this).attr('src');
        if (PWicon === '/resources/img/icon/비밀번호안보임.png') {
            $(this).attr('src', '/resources/img/icon/비밀번호보임.png');
			$('.userPW').prop('type', 'text');
        } else {
            $(this).attr('src', '/resources/img/icon/비밀번호안보임.png');
			$('.userPW').prop('type', 'password');
        }
	});
});
/* 비밀번호 보안 */
function passwordsecurity() {
	var regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()\.])[a-zA-Z\d!@#$%^&*()\.]{8,}$/;
    var userPW = $('.userPW').val();
    if (regex.test(userPW)) {
        $("#passwordLabel").text("안전한 아이디입니다");
		$("#passwordLabel").css("color", "rgb(130, 165, 217)");
    } else if (userPW ==="" || userPW===null){
		$("#passwordLabel").css("color", "#757575");
	}
	else {
        $("#passwordLabel").text("비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이여야합니다");
		$("#passwordLabel").css("color", "rgb(229, 119, 119)");
    }
}