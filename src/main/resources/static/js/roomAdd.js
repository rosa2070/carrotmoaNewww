// Kakao Maps API가 로드되면 실행되는 함수
let geoCoder;

// Kakao Maps API가 로드되면 geoCoder 초기화
function initMap() {
    geoCoder = new kakao.maps.services.Geocoder();
}

// 주소로 위도, 경도를 가져오는 함수
function getAddressCoords(address) {
    return new Promise((resolve, reject) => {
        if (geoCoder) {
            geoCoder.addressSearch(address, (result, status) => {
                if (status === kakao.maps.services.Status.OK && result.length > 0) {
                    const coords = new kakao.maps.LatLng(result[0].y, result[0].x); // 위도, 경도
                    resolve(coords);
                } else {
                    reject('주소 검색 실패: ' + status);
                }
            });
        } else {
            reject('geoCoder가 정의되지 않았습니다.');
        }
    });
}

// 주소 검색 함수
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('roadAddress').value = data.roadAddress; // 도로명 주소
            document.getElementById('lotAddress').value = data.jibunAddress; // 지번 주소

            const mainAddress = data.roadAddress || data.jibunAddress;
            getAddressCoords(mainAddress)
                .then(coords => {
                    document.getElementById('latitude').value = coords.getLat(); // 위도
                    document.getElementById('longitude').value = coords.getLng(); // 경도
                })
                .catch(error => {
                    console.error('좌표를 가져오는 중 오류 발생:', error);
                    alert('좌표를 가져오는 데 문제가 발생했습니다. 다시 시도해주세요.');
                });
        }
    }).open();
}

// DOMContentLoaded 이벤트로 initMap 호출 및 버튼 클릭 이벤트 추가
document.addEventListener('DOMContentLoaded', () => {
    initMap();
    document.getElementById('btn_search_addr').addEventListener('click', sample4_execDaumPostcode);
});



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



function submitForm(event) {
    event.preventDefault(); // 기본 제출 이벤트 방지

    const formData = new FormData(document.getElementById('registerForm'));

    // 필드 데이터 확인 (디버깅 용도)
    for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
    }

    fetch('/api/host/room/register', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    // 오류 처리 (예: 오류 메시지 표시)
                    console.error('오류 발생:', err);
                });
            }
            return response.json();
        })
        .then(data => {
            // 성공적으로 데이터가 전송된 후 처리 (예: 성공 메시지 표시)
            console.log('숙소 등록 성공:', data);
            window.location.href = '/host/room/manage'; // 방 관리 페이지로 이동

        })
        .catch(error => {
            console.error('서버 오류:', error);
            alert('서버와의 통신 중 오류가 발생했습니다. 다시 시도해주세요.'); // 사용자에게 알림
        });
}

