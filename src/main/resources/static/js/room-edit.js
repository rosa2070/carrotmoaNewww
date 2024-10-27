let geoCoder; // 지오코더 변수 선언
let originalData = {}; // 원본 데이터 저장

// 지도 초기화 함수
function initMap() {
    geoCoder = new kakao.maps.services.Geocoder(); // 카카오 지오코더 인스턴스 생성
}

// 주소로부터 좌표를 가져오는 함수
function getAddressCoords(address) {
    return new Promise((resolve, reject) => {
        if (geoCoder) {
            geoCoder.addressSearch(address, (result, status) => {
                if (status === kakao.maps.services.Status.OK && result.length > 0) {
                    const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
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

// 주소 찾기 기능을 수행하는 함수
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('roadAddress').value = data.roadAddress; // 도로명 주소 입력
            document.getElementById('lotAddress').value = data.jibunAddress; // 지번 주소 입력

            const mainAddress = data.roadAddress || data.jibunAddress;
            getAddressCoords(mainAddress)
                .then(coords => {
                    document.getElementById('latitude').value = coords.getLat();
                    document.getElementById('longitude').value = coords.getLng();
                })
                .catch(error => {
                    console.error('좌표를 가져오는 중 오류 발생:', error);
                    alert('좌표를 가져오는 데 문제가 발생했습니다. 다시 시도해주세요.');
                });
        }
    }).open(); // 주소 검색 창 열기
}

// 필드 유효성 검사 함수
function validateField(fieldId, message, isNumber = false) {
    const value = document.getElementById(fieldId).value.trim();
    if (!value) {
        alert(message);
        document.getElementById(fieldId).focus();
        return false;
    }

    if (isNumber && (isNaN(value) || value <= 0)) {
        alert('입력 값은 숫자여야 하며, 0보다 커야 합니다.');
        return false;
    }
    return true;
}

// 전체 폼 유효성 검사 함수
function validateForm() {
    const validations = [
        { id: 'title', message: '숙소 이름은 필수 입력입니다.' },
        { id: 'roadAddress', message: '도로명 주소는 필수 입력입니다.' },
        { id: 'lotAddress', message: '지번 주소는 필수 입력입니다.' },
        { id: 'detailAddress', message: '상세 주소는 필수 입력입니다.' },
        { id: 'floor', message: '층 수는 필수 입력입니다.', isNumber: true },
        { id: 'totalFloor', message: '총 층 수는 필수 입력입니다.', isNumber: true },
        { id: 'totalArea', message: '총 면적은 필수 입력입니다.', isNumber: true },
        { id: 'price', message: '가격은 필수 입력입니다.', isNumber: true },
        { id: 'content', message: '상세 설명은 필수 입력입니다.' },
        { id: 'transportationInfo', message: '교통 정보는 필수 입력입니다.' },
    ];

    for (const { id, message, isNumber } of validations) {
        if (!validateField(id, message, isNumber)) return false;
    }

    const imageInput = document.getElementById('images');
    if (imageInput.files.length < 4) {
        alert('방 사진은 최소 4장 이상 등록해야 합니다.');
        return false;
    }

    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
    if (!isChecked) {
        alert('기본 옵션을 하나 이상 선택해야 합니다.');
        return false;
    }

    return true;
}

// 폼 제출 처리 함수
function handleFormSubmission(event) {
    if (!validateForm()) return;

    const confirmSubmission = confirm('입력하신 내용으로 수정하시겠습니까?');
    if (!confirmSubmission) return;

    const accommodationId = window.location.pathname.split('/').pop();
    const formData = new FormData(event.target);

    const existingImageUrls = originalData.imageUrls || [];
    existingImageUrls.forEach(url => formData.append('existingImageUrls', url));

    const spaceIds = [1, 2, 3, 4];
    const spaceCounts = [
        parseInt(document.getElementById('room_cnt').value),
        parseInt(document.getElementById('bathroom_cnt').value),
        parseInt(document.getElementById('livingRoom_cnt').value),
        parseInt(document.getElementById('kitchen_cnt').value)
    ];

    spaceIds.forEach((spaceId, index) => {
        formData.append(`accommodationSpaces[${index}].spaceId`, spaceId);
        formData.append(`accommodationSpaces[${index}].count`, spaceCounts[index]);
    });

    const currentAmenities = Array.from(
        document.querySelectorAll('input[type="checkbox"]:checked')
    ).map(checkbox => Number(checkbox.value));

    currentAmenities.forEach(amenityId => formData.append('amenityIds', amenityId));

    fetch(`/api/host/room/edit/${accommodationId}`, {
        method: 'PATCH',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    const errorMessages = Array.isArray(err) ? err : [err.message || "알 수 없는 오류"];
                    alert('유효성 검사 오류:\n' + errorMessages.join('\n'));
                    throw new Error('유효성 검사 실패');
                });
            }
            alert('숙소 정보가 수정되었습니다.');
            window.location.href = '/host/room/manage';
        })
        .catch(error => {
            console.error('수정 중 오류 발생:', error);
            alert('서버와의 통신 중 오류가 발생했습니다. 다시 시도해주세요.');
        });
}

// DOMContentLoaded 이벤트 리스너
document.addEventListener('DOMContentLoaded', () => {
    initMap();
    document.getElementById('btn_search_addr').addEventListener('click', sample4_execDaumPostcode);

    const accommodationId = window.location.pathname.split('/').pop();
    fetch(`/api/host/room/${accommodationId}`)
        .then(response => {
            if (!response.ok) throw new Error("숙소 정보를 가져오는 중 오류 발생");
            return response.json();
        })
        .then(data => {
            originalData = data;
            populateFormFields(data);
            showImagePreviews(data.imageUrls);
            checkAmenities(data.amenityIds);
        })
        .catch(error => {
            console.error('오류:', error);
        });

    const updateForm = document.getElementById('updateForm');
    updateForm.addEventListener('submit', function (event) {
        event.preventDefault();
        handleFormSubmission(event);
    });

    document.getElementById('images').addEventListener('change', function (event) {
        const files = event.target.files;
        const imageContainer = document.getElementById('div_added_pictures');
        imageContainer.innerHTML = '';

        Array.from(files).forEach(file => {
            const reader = new FileReader();
            reader.onload = function (e) {
                const imageItem = document.createElement('div');
                imageItem.className = 'image_item';
                imageItem.innerHTML = `<img src="${e.target.result}" alt="미리보기 이미지">`;
                imageContainer.appendChild(imageItem);
            };
            reader.readAsDataURL(file);
        });
    });
});

// 폼 필드를 원본 데이터로 채우는 함수
function populateFormFields(data) {
    const fields = [
        'title', 'roadAddress', 'lotAddress', 'detailAddress',
        'floor', 'totalFloor', 'totalArea', 'price',
        'content', 'transportationInfo'
    ];

    fields.forEach(field => {
        document.getElementById(field).value = data[field]; // 각 필드 채우기
    });

    // 공간 수 채우기
    const spaceCounts = data.spaceCounts || [];
    document.getElementById('room_cnt').value = spaceCounts[0] || 0;
    document.getElementById('bathroom_cnt').value = spaceCounts[1] || 0;
    document.getElementById('livingRoom_cnt').value = spaceCounts[2] || 0;
    document.getElementById('kitchen_cnt').value = spaceCounts[3] || 0;
}

// 선택된 편의시설 체크하는 함수
function checkAmenities(amenityIds) {
    amenityIds.forEach(amenityId => {
        const checkbox = document.getElementById(`opt_basic_${amenityId}`);
        if (checkbox) {
            checkbox.checked = true;
        }
    });
}

// 이미지 미리보기 표시 함수
function showImagePreviews(imageUrls) {
    const imageContainer = document.getElementById('div_added_pictures');
    imageContainer.innerHTML = '';

    imageUrls.forEach(imageUrl => {
        const imageItem = document.createElement('div');
        imageItem.className = 'image_item';
        imageItem.innerHTML = `<img src="${imageUrl}" alt="미리보기 이미지">`;
        imageContainer.appendChild(imageItem);
    });
}
