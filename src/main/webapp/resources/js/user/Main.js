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


/*슬라이드*/
$(document).ready(function () {
  let slides = $('.slides');
  let slideImg = $('.slides div');
  let currentIdx = 0;
  let slideCount = slideImg.length;
  let slideWidth = 300;
  let slideMargin = 50;
  let moveWidth = slideWidth + slideMargin; /* 슬라이드 하나당 이동해야 하는 거리 */
  let visibleSlides = 3; // 화면에 보이는 상품의 개수

  function moveSlide(num) {
    slides.css('transform', 'translateX(' + (-moveWidth * num) + 'px)');
    currentIdx = num;
  }

  $('.next').click(function () {
    if (currentIdx < slideCount - visibleSlides) { // 세 개의 상품이 보이므로 -3
      moveSlide(currentIdx + 1);
    } else {
      moveSlide(0);
    }
  });

  $('.prev').click(function () {
    if (currentIdx > 0) {
      moveSlide(currentIdx - 1);
    } else {
      moveSlide(slideCount - visibleSlides); // 세 개의 상품이 보이므로 -3
    }
  });
});
