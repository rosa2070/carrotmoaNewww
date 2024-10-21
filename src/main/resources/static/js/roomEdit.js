// 카카오 주소 찾기
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('roadAddress').value = roadAddr;
            document.getElementById('lotAddress').value = data.jibunAddress;
        }
    }).open();
}

// 이미지 미리보기
function showImagePreviews(imageUrls) {
    const imageContainer = document.getElementById('div_added_pictures');
    imageContainer.innerHTML = ''; // 기존 미리보기 이미지 제거

    imageUrls.forEach(imageUrl => {
        const imageItem = document.createElement('div');
        imageItem.className = 'image_item';
        imageItem.innerHTML = `<img src="${imageUrl}" alt="미리보기 이미지">`;
        imageContainer.appendChild(imageItem);
    })
}

// 이미지 미리보기에 등록
document.getElementById('images').addEventListener('change', function(event) {
    const files = event.target.files;
    const imageContainer = document.getElementById('div_added_pictures');
    imageContainer.innerHTML = ''; // 기존 미리보기 이미지 제거

    Array.from(files).forEach(file => {
        const reader = new FileReader();

        reader.onload = function(e) {
            // 이미지 미리보기 생성
            const imageItem = document.createElement('div');
            imageItem.className = 'image_item';
            imageItem.innerHTML = `<img src="${e.target.result}" alt="미리보기 이미지">`;
            imageContainer.appendChild(imageItem);
        };

        reader.readAsDataURL(file);


    })
})

// 경고 문구
document.querySelector('form').addEventListener('submit', function(event) {
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
    }
});

// 원래 데이터 뿌려주기
document.addEventListener("DOMContentLoaded", function () {
    const accommodationId = window.location.pathname.split('/').pop(); // URL에서 ID 추출
    fetch(`/api/host/room/${accommodationId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("네트워크 응답이 올바르지 않습니다.");
            }
            return response.json();
        })
        .then(data => {
            // 폼 필드에 데이터 삽입
            //userId 추가해야하나??
            document.getElementById('accommodationId').value = data.id;
            document.getElementById('name').value = data.title;
            document.getElementById('roadAddress').value = data.roadAddress;
            document.getElementById('lotAddress').value = data.lotAddress;
            document.getElementById('detailAddress').value = data.detailAddress;
            document.getElementById('floor').value = data.floor;
            document.getElementById('totalFloor').value = data.totalFloor;
            document.getElementById('totalArea').value = data.totalArea;
            document.getElementById('room_cnt').value = data.spaceCounts[0];
            document.getElementById('bathroom_cnt').value = data.spaceCounts[1];
            document.getElementById('livingRoom_cnt').value = data.spaceCounts[2];
            document.getElementById('kitchen_cnt').value = data.spaceCounts[3];
            document.getElementById('price').value = data.price;
            document.getElementById('detail').value = data.content;
            document.getElementById('transportationInfo').value = data.transportationInfo;

            // 이미지 미리보기
            showImagePreviews(data.imageUrls);

            // 선택된 amenityIds 체크
            data.amenityIds.forEach(amenityId => {
                const checkbox = document.getElementById(`opt_basic_${amenityId}`);
                if (checkbox) {
                    checkbox.checked = true; // 체크 상태로 설정
                }
            });



        })
        .catch(error => {
            console.error('숙소 정보를 가져오는 중 오류 발생: ', error);
        })
})