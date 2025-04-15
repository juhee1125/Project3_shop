function saveAndRedirect(num) {
    const heartImgSrc = "path_to_heart_image"; // 현재 채워진 하트 이미지의 경로를 가져와 설정
    sessionStorage.setItem("heartImgSrc", heartImgSrc); // heartImgSrc를 세션 스토리지에 저장
    window.location.href = `/detail?num=${num}`;
}