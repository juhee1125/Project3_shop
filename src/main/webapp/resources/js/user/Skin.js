$(document).ready(function() {
    $('.heartimg').click(function() {
		var productName = $(this).closest('.product-set').find('.namediv .productlabel').text();
		if (!productName) {
            var productName = $(this).closest('.product-set').find('.productlabel').text() 
                  || $(this).closest('.infodiv').find('.infodivlabel').text();
			console.log(productName)
        }

		var productNum = $(this).closest('.product-set').data('product-num');
		console.log("productNum",productNum)
		
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
			"productName": productName,
			"productNum": productNum
		}
		$.ajax({
	        type: 'POST',
	        url: window.location.pathname.includes("shop") ? '/shop/likes' : '/likes',
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
    var productname = $(this).data('productname'); 
    gotodetail(productname);
});

$('.namediv label').unbind().click(function() {
    var productname = $(this).text();
    gotodetail(productname);
});
