/* 문의답변 */
$(document).ready(function() {
	$('.productbutton').click(function() {
		var qtitle = $('.qtitle').text();
		var qid = $('.qid').text();
		var qcontent = $('.qcontent').text();
		var textbox = $('.textbox').val();
		
	    $.ajax({
	        type: 'Post',
	        url: '/admin/QnAUpdateprocess',
			contentType: "application/json",
	        data: JSON.stringify({
			    "qtitle": qtitle,
			    "qid": qid,
			    "qcontent": qcontent,
			    "textbox": textbox
			}),
	        success: function (data) {
				console.log("업데이트 성공")
				if (data.message==="답변 완료하였습니다"){
					uploadsuccessalert("답변 완료하였습니다")
				}

	        },
	        error: function (xhr, status, error) {
		        console.log("업데이트 실패");
		        console.error("Error details: ", xhr, status, error);
		    }
	    });
	});
});
function uploadsuccessalert(message) {	
    Swal.fire({
        text: message,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then(function(){
		location.href='/admin/QnA';
	})
};