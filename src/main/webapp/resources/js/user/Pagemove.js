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