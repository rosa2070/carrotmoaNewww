// data-user-id 속성에서 사용자 ID를 가져오기
const userId = document.getElementById('user-data').getAttribute("data-user-id");
// console.log(`사용자 ID: ${userId}`);

document.addEventListener('DOMContentLoaded', async () => {
    try {
        // API 호출하여 예약 정보 가져오기
        // console.log(`Calling API with userId: ${userId}`);
        const response = await fetch(`/api/host/reservation/${userId}`); // 실제 API URL로 변경
        if (!response.ok) {
            throw new Error('예약 정보를 가져오는 데 실패했습니다.');
        }

        const reservations = await response.json(); // JSON 형태로 파싱

        const hostContractDiv = document.querySelector('.guest_contract');
        hostContractDiv.innerHTML = ''; // 기존 계약 항목 초기화

        reservations.forEach(reservation => {
            // 계약 취소 상태는 제외
            if (reservation.status === 2) return; // 상태가 2인 경우(계약 취소)는 건너뛰기

            // 계약 아이템 생성
            const contractItem = document.createElement('div');
            contractItem.className = 'contract_item';

            // status에 따라 배지 텍스트 설정
            let badgeText;
            let badgeClass = '';

            switch (reservation.status) {
                case 1:
                    badgeText = '계약 완료';
                    badgeClass = 'orange';
                    break;
                case 2:
                    badgeText = '계약 취소';
                    badgeClass = 'gray';
                    break;
                case 3:
                    badgeText = '계약 만료';
                    badgeClass = 'gray';
                    break;

                // 다른 상태가 있을 경우 추가
                // default:
                //     badgeText = '알 수 없음';
                //     break;
            }

            const contractHeader = `
                <div class="contract_header">
                    <div class="flex contract_info" style="cursor:pointer;" onclick="window.location.href='/room/detail/${reservation.accommodationId}'">
                        <span class="badge ${badgeClass}">${badgeText}</span>
                        <p class="room_title">${reservation.title}</p>
                    </div>
                </div>
            `;

            const contractContent = `
                <div class="contract_cont" style="align-items:center">
                    <dl class="room_item" style="cursor:pointer;" onclick="window.location.href='/room/detail/${reservation.accommodationId}'">
                        <dt>
                            <img class="room_img" src="${reservation.imageUrl}" alt="">
                        </dt>
                        <dd>
                            <div class="room_address ellipsis">${reservation.lotAddress} ${reservation.detailAddress} ${reservation.floor}층</div>
                            <div class="room_address ellipsis">${reservation.guestName}</div>
                            <p class="room_period">${reservation.checkInDate} ~ ${reservation.checkOutDate}</p>
                        </dd>
                    </dl>
                    <strong class="room_pay">${reservation.totalPrice} 원</strong>
                </div>
            `;

            // 전체 계약 아이템 HTML 조합
            contractItem.innerHTML = contractHeader + contractContent;

            // 클릭 시 페이지 이동
            // contractItem.onclick = () => {
            //     window.location.href = `/guest/room/detail/${reservation.accommodationId}`;
            // }


            // 계약 리스트에 추가
            hostContractDiv.appendChild(contractItem);





        });
    } catch (error) {
        console.error('오류 발생:', error);
        alert('예약 정보를 불러오는 중 오류가 발생했습니다: ' + error.message);
    }
})