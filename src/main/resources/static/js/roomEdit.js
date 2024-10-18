document.addEventListener("DOMContentLoaded", function () {
    const accommodationId = window.location.pathname.split('/').pop(); // URL에서 ID 추출
    fetch(`/host/room/${accommodationId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("네트워크 응답이 올바르지 않습니다.");
            }
            return response.json();
        })
        .then(data => {
            // 폼 필드에 데이터 삽입
            //userId 추가해야하나??
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
            const imageContainer = document.getElementById('div_added_pictures');
            imageContainer.innerHTML = ''; // 기존 미리보기 제거
            data.imageUrls.forEach(imageUrl => {
                const imageItem = document.createElement('div');
                imageItem.className = 'image_item';
                imageItem.innerHTML = `<img src="${imageUrl}" alt="미리보기 이미지">`;
                imageContainer.appendChild(imageItem);
            });

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