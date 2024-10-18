function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('roadAddress').value = data.roadAddress;
            document.getElementById('lotAddress').value = data.jibunAddress;
        }
    }).open();
}


// 이미지 미리보기에 등록
document.getElementById('images').addEventListener('change', function (event) {
    const files = event.target.files;
    const imageContainer = document.getElementById('div_added_pictures');
    imageContainer.innerHTML = ''; // 기존 미리보기 이미지 제거

    Array.from(files).forEach(file => {
        const reader = new FileReader();

        reader.onload = function (e) {
            // 이미지 미리보기 생성
            const imageItem = document.createElement('div');
            imageItem.className = 'image_item';
            imageItem.innerHTML = `<img src="${e.target.result}" alt="미리보기 이미지">`;
            imageContainer.appendChild(imageItem);
        };

        reader.readAsDataURL(file);
    });
});

document.getElementById('registerForm').addEventListener('submit', function (event) {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

    const imageInput = document.getElementById('images');
    const files = imageInput.files;

    // 이미지 등록 체크
    if (files.length < 4) { // 최소 4장 체크
        event.preventDefault(); // 제출 막기
        alert('방 사진은 최소 4장 이상 등록해야 합니다.'); // 경고 메시지
        return; // 추가 검사 중단
    }

    if (!isChecked) {
        event.preventDefault(); // 제출 막기
        alert('기본 옵션을 하나 이상 선택해야 합니다.'); // 경고 메시지
        return; // 추가 검사 중단
    }

});
