/*반응형 웹*/
function updateImageSrc() {
	var img = document.getElementById('img1');
	var width = window.innerWidth;
	
	if (width <= 600) {
	  img.src = "resources/img/메인이미지2모바일.jpg";
	}
	else {
		img.src = "resources/img/메인이미지2.jpg";
	}
}

window.addEventListener('resize', updateImageSrc); // 창 크기 조정 시 호출
window.addEventListener('load', updateImageSrc);