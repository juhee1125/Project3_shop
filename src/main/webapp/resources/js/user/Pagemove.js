// 상품상세페이지로 이동
$(document).ready(function() {
    $('.productlabel, .mainimg').click(function() {
		if ($(this).hasClass('productlabel')) {
            productlabel = $(this).text();
        }
		else {
            productlabel = $(this).siblings('.productlabel').text(); 
			if (!productlabel) {
                productlabel = $(this).closest('.product-set').find('.namediv .productlabel').text();
            }
		}
		var heartImgSrc = $('.icon-container').find('.heartimg').attr('src');
		if(!heartImgSrc){
			console.log("1번")
			heartImgSrc = $(this).closest('.product-set').find('.imgdiv .heartimg').attr('src');
		}
		console.log(productlabel)
		console.log(heartImgSrc)
		const data={
			"productlabel": productlabel,
			"heartImgSrc": heartImgSrc
		}
		$.ajax({
	        type: 'POST',
	        url: "/detail/detailprocess",
			contentType: "application/json",
	        data: JSON.stringify(data),
			dataType: 'text',
	        success: function (data) {
				console.log("넘어옴")
				window.location.href = "/detail/detail";
	        }
	    });
    });
});

// 구매페이지로 이동
$(document).ready(function() {
    $('.imgdivbutton').click(function() {
		heartImgSrc = $(this).closest('.product-set').find('.imgdiv .heartimg').attr('src');

		const data={
			"productlabel": productlabel,
			"heartImgSrc": heartImgSrc
		}
		$.ajax({
	        type: 'POST',
	        url: "/detail/detailprocess",
			contentType: "application/json",
	        data: JSON.stringify(data),
			dataType: 'text',
	        success: function (data) {
				console.log("넘어옴")
				window.location.href = "/detail/detail";
	        }
	    });
    });
});