// 카카오 주소 찾기
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
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


    })
})

// 경고 문구
// document.querySelector('form').addEventListener('submit', function(event) {
//     const checkboxes = document.querySelectorAll('input[type="checkbox"]');
//     const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
//
//     const imageInput = document.getElementById('images');
//     const files = imageInput.files;
//
//     // 이미지 등록 체크
//     if (files.length < 4) { // 최소 4장 체크
//         event.preventDefault(); // 제출 막기
//         alert('방 사진은 최소 4장 이상 등록해야 합니다.'); // 경고 메시지
//         return; // 추가 검사 중단
//     }
//
//     if (!isChecked) {
//         event.preventDefault(); // 제출 막기
//         alert('기본 옵션을 하나 이상 선택해야 합니다.'); // 경고 메시지
//     }
// });

let accommodationId;

// s3는 데이터 받아올때 유의
document.addEventListener("DOMContentLoaded", function () {
    accommodationId = window.location.pathname.split('/').pop(); // URL에서 ID 추출
    const images = document.getElementById('images');
    let originalData = {}; // 원본 데이터 저장

    // 숙소 정보 가져오기
    fetch(`/api/host/room/${accommodationId}`)
        .then(response => {
            if (!response.ok) {
                console.error('응답 상태:', response.status);
                throw new Error("업데이트 실패: " + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            originalData = data; // 원본 데이터 저장

            // 폼 필드에 데이터 삽입
            document.getElementById('title').value = data.title;
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
            document.getElementById('content').value = data.content;
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
        });

    // 폼 제출 이벤트 처리
    document.getElementById('updateForm').addEventListener('submit', handleSubmit);
});

function handleSubmit(event) {
    event.preventDefault();
    const updatedData = new FormData();
    const fields = ['title', 'roadAddress', 'lotAddress', 'detailAddress',
        'floor', 'totalFloor', 'totalArea', 'price', 'content', 'transportationInfo'];

    fields.forEach(field => {
        const value = document.getElementById(field).value;
        if (value) {
            updatedData.append(field, value);
            console.log(`변경된 필드: ${field} => ${value}`); // 변경된 필드 로깅
        }
    });

    // 공간 수 매핑 및 추가
    const spaceIds = [1, 2, 3, 4];
    const spaceCounts = [
        parseInt(document.getElementById('room_cnt').value),
        parseInt(document.getElementById('bathroom_cnt').value),
        parseInt(document.getElementById('livingRoom_cnt').value),
        parseInt(document.getElementById('kitchen_cnt').value)
    ];

    spaceIds.forEach((spaceId, index) => {
        updatedData.append(`accommodationSpaces[${index}].spaceId`, spaceId);
        updatedData.append(`accommodationSpaces[${index}].count`, spaceCounts[index]);
    })

    // 편의 시설 ID 추가
    const currentAmenities =
        Array.from(document.querySelectorAll('input[type="checkbox"]:checked')).map(checkbox => Number(checkbox.value));
    currentAmenities.forEach(amenityId => {
        updatedData.append('amenityIds', amenityId);
    })


    // PATCH 요청
    fetch(`/api/host/room/edit/${accommodationId}`, {
        method: 'PATCH',
        body: updatedData
    })
        .then(response => {
            alert('숙소 정보가 수정되었습니다.'); // 성공 메시지 표시
            // 필요 시 리디렉션 처리
            window.location.href = `/host/room/manage`; // 방 관리 페이지로 이동
        })
        .catch(error => {
            console.error('수정 중 오류 발생:', error);
        });
}


