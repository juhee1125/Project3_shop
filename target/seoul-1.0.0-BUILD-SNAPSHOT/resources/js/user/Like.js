$(document).ready(function() {
    $('.heartimg').click(function() {
		var productName = $(this).closest('.product-set').find('.namediv label').text();
		var productSet = $(this).closest('.product-set');
		
		var data={
			"productName": productName
		}
		productSet.remove();

		$.ajax({
	        type: 'POST',
	        url: '/like/likeprocess',
			contentType: "application/json",
	        data: JSON.stringify(data),
			dataType: 'text',
	        success: function (data) {
				console.log(data)
				location.reload();
	        }
	    });
    });
});

$(document).ready(function() {
	var savedJob = localStorage.getItem('selectedJob');
    if (savedJob) {
        $('#job').val(savedJob);
		localStorage.removeItem('selectedJob');
    }
	else {
		$('#job').val('all');
	}
	console.log(savedJob)
	$('#job').change(function() {
		var job = $("#job").val();
		localStorage.setItem('selectedJob', job);
		
		$.ajax({
	        type: 'GET',
	        url: "/like/likeselect",
	        data: { 
				'job': job
			},
	        success: function (data) {
				location.reload();
	        },
	        error: function (error) {
	            console.error("오류발생");
	        }
	    });
	});
});