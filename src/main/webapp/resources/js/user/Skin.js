$(document).ready(function() {
    $('.heartimg').click(function() {
		var productName = $(this).closest('.product-set').find('.namediv label').text();
		var likeaction="";
		
		if ($(this).hasClass('liked')) {
            console.log('좋아요 취소');
            $(this).removeClass('liked');
			$(this).attr('src', '/resources/img/icon/찜.png');
			likeaction="unliked";
        } else {
            console.log('좋아요 추가');
			$(this).addClass('liked');
			$(this).attr('src', '/resources/img/icon/채운찜.png');
			likeaction="liked";
        }
		var data={
			"likeaction": likeaction,
			"productName": productName
		}
		console.log(likeaction)
		$.ajax({
	        type: 'POST',
	        url: '/shop/likes',
			contentType: "application/json",
	        data: JSON.stringify(data),
			dataType: 'text',
	        success: function (data) {
				console.log(data)
				if (data === "로그인 후 가능합니다"){
					likewarningalert(data)
				}
	        }
	    });
    });
});
function likewarningalert(message) {
    Swal.fire({
        text: message,
        icon: 'warning',
        confirmButtonText: 'OK'
    }).then(function(){
		location.href='/login/login';
	})
};

function gotodetail(productname) {
	console.log(productname)
	$.ajax({
        type: 'GET',
        url: "/shop/detail",
        data: { 
			'productname': productname
		},
        success: function (data) {
			console.log("성공");
			/*location.reload();*/
			location.href='/shop/detail';
        }
    });
}
$('.mainimg').unbind().click(function() {
    var productname = $(this).data('productname'); // 예를 들어, data 속성을 이용하여 상품명을 가져올 수 있음
    gotodetail(productname);
});

$('.namediv label').unbind().click(function() {
    var productname = $(this).text(); // label의 텍스트를 상품명으로 사용할 경우
    gotodetail(productname);
});
