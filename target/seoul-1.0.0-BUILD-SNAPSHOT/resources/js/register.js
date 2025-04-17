var isDuplicateChecked = false;
var previousCheckedID = ""
/* 회원가입 버튼 클릭 시 작동 */
$(document).ready(function() {
	$('.registerB').click(function(e) {
		e.preventDefault();
		var userName = $('.userName').val();
		var userID = $('.userID').val();
	    var userPW = $('.userPW').val();
		var passwordcheck = $('.passwordcheck').val();
	    var userPhone = $('.userPhone').val();
	    var userEmail = $('.userEmail').val();
		var emailinput = $('.emailinput').val();
	    var Postalcode = $('#Postalcode').val();
		var userAddress = $('#userAddress').val();
		var userdetailAddress = $('#userdetailAddress').val();

		const datalist ={"userName": userName,
				"userID": userID,
	            "userPW": userPW,
				"passwordcheck": passwordcheck,
	            "userPhone": userPhone,
	            "userEmail": userEmail,
				"emailinput": emailinput,
	            "userAddress": userAddress,
				"Postalcode": Postalcode,
				"userdetailAddress": userdetailAddress
				}
		
	    $.ajax({
	        type: 'POST',
	        url: '/login/registerprocess',
			contentType: "application/json",
	        data: JSON.stringify(datalist),
			dataType: 'text',
	        success: function (data) {
	            console.log(data);
	            if (data === "회원가입을 완료했습니다" && $("#passwordLabel").text() === "안전한 아이디입니다" 
					&& $(".userPW").val()===$(".passwordcheck").val()) {
	                console.log("회원가입 성공");
                    registersuccessalert(data);
	            }
	        },
	        error: function () {
				console.log("회원가입 실패");
				if (isDuplicateChecked == false){
					idwarningalert("아이디를 확인해주세요")
				}
				else if ($("#passwordLabel").text() !== "안전한 아이디입니다" || $(".userPW").val()!==$(".passwordcheck").val()){
					idwarningalert("비밀번호를 확인해주세요")
				}
				else {
					registerwarningalert("필수항목을 입력해주세요");
				}
	        }
	    });
	});
});
function registersuccessalert(message) {
	console.log("회원가입 js로 넘어옴");
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
		location.href='/login/login';
	})
};
function registerwarningalert(message) {
	console.log("회원가입 js로 넘어옴");
	console.log(message);
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    }).then(function(){
		var inputs = $('input[required]');
		inputs.each(function() {
			/* 회원가입 필수입력 미입력 시 outline 변경 */
            if (!this.checkValidity()) {
                $(this).css('outline', '2px solid #e57777');
            } else {
				$(this).css('outline', '');
			}
        });
	});
};

/* 아이디 중복확인 */
$(document).ready(function() {
    $('.duplicateB').click(function() {
        var userID = $('.userID').val();
        
        $.ajax({
            type: 'POST',
            url: '/login/duplicateprocess',
            contentType: "application/json",
            data: JSON.stringify({"userID": userID}),
            dataType: 'text',
            success: function (data) {
                console.log(data);
                if (data === "사용가능한 아이디입니다") {
                    console.log("사용가능 아이디");
                    $("#duplicateLabel").text(data);
					$("#duplicateLabel").css('color', '#82a5d9');
					isDuplicateChecked = true;
                }
				else {
					idwarningalert(data);
				}
				newIDalert("이미 중복확인한 아이디입니다", userID);
				previousCheckedID = userID;
            },
            error: function () {
                console.log("사용중인 아이디");
                $("#duplicateLabel").text("이미 사용중인 아이디입니다");
				$("#duplicateLabel").css('color', 'rgb(229, 119, 119)');
				isDuplicateChecked = false;
				previousCheckedID = userID;
				console.log("previousCheckedID",previousCheckedID);
            }
        });
    });
	/* 아이디 수정 시 중복확인하게끔 */
	$('.userID').change(function() {
		isDuplicateChecked = false;
		$("#duplicateLabel").text("");
	});
});
function idwarningalert(message) {
	console.log("아이디입력");
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    })
};
/* 이중중복확인 방지 */
function newIDalert(message, newID) {
	console.log("중복확인 방지")
	console.log("previousCheckedID",previousCheckedID,"newID", newID)
    if (previousCheckedID === newID) {
        idwarningalert(message)
    }
	else {
		previousCheckedID ==""
	}
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

/* 이메일 */
$(document).ready(function() {
	$('.emailselect').change(function() {
		var emailselect = $(this).val();
		$('input.emailinput').val(emailselect);
	});
});

/* 주소 검색 */
function addresssearch() {
    new daum.Postcode({
        oncomplete: function(data) {
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('Postalcode').value = data.zonecode;
            document.getElementById("userAddress").value = fullRoadAddr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("userdetailAddress").focus();
        }
    }).open();
}