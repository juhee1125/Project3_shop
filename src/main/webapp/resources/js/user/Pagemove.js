// 상품상세페이지로 이동
$(document).ready(function() {
    $('.productlabel, .mainimg').click(function() {
		var heartImgSrc = $('.icon-container').find('.heartimg').attr('src');
		if(!heartImgSrc){
			heartImgSrc = $(this).closest('.product-set').find('.imgdiv .heartimg').attr('src');
		}
		var num = $(this).closest('.product-set').data('product-num');
		console.log("productNum",num)

		$.ajax({
            type: 'POST',
            url: "/detail/saveHeartImg",
            data: { 'heartImgSrc': heartImgSrc },
            success: function () {
                console.log("heartImgSrc 세션에 저장 완료");
                window.location.href = "/detail/detail?num=" + encodeURIComponent(num);
            }
        });
    });
});