// data-user-id 속성에서 사용자 ID를 가져오기
const userId = document.getElementById('user-data').getAttribute('data-user-id');

// DOMContentLoaded 이벤트에서 방 이름 목록을 가져옵니다.
document.addEventListener('DOMContentLoaded', async () => {
    try {
        // console.log(`Calling API with userId: ${userId}`);

        // 방 이름 목록을 가져오기 위한 API 호출
        const response = await fetch(`/api/host/room/manage/${userId}`); // 실제 API URL로 변경
        if (!response.ok) {
            throw new Error('방 이름을 가져오는 데 실패했습니다.');
        }

        const rooms = await response.json(); // JSON 형태로 파싱
        const roomSelect = document.getElementById('opt_room_id');

        // 기존 옵션 제거
        roomSelect.innerHTML = '<option value="0">방 이름 - 전체</option>';

        // 방 이름을 select에 추가
        rooms.forEach(room => {
            const option = document.createElement('option');
            option.value = room.accommodationId; // 방 ID 설정 (예: room.id)
            option.textContent = room.title; // 방 이름 설정 (예: room.name)
            roomSelect.appendChild(option);
        });


        // Flatpickr 초기화
        flatpickr("#s_start_date", {
            dateFormat: "Y-m-d", // 원하는 형식으로 설정 가능
        });
        flatpickr("#s_end_date", {
            dateFormat: "Y-m-d", // 원하는 형식으로 설정 가능
        });

        // 검색 버튼 클릭 이벤트 리스너
        document.getElementById('btn_search').addEventListener('click', async function () {
            const startDate = document.getElementById('s_start_date').value;
            const endDate = document.getElementById('s_end_date').value;
            const title = roomSelect.value; // 방 이름 선택 (나중에 동적 구현)

            try {
                const response = await fetch(`/api/settlement?title=${title}&startDate=${startDate}&endDate=${endDate}`);
                if (!response.ok) {
                    throw new Error("네트워크 응답이 올바르지 않습니다.");
                }

                const data = await response.json();
                const tableBody = document.getElementById('settlement_table_body');
                tableBody.innerHTML = ''; // 기존 테이블 내용 초기화
                let totalCount = 0;
                let totalAmount = 0;

                // 데이터를 테이블에 추가
                data.forEach(item => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${item.settlementDate}</td>
                        <td>${item.title}</td>
                        <td>${item.name}</td>
                        <td>${item.checkInDate}</td>
                        <td>${item.paymentAmount}원</td>
                    `;
                    tableBody.appendChild(row);
                    totalCount++;
                    totalAmount += parseInt(item.paymentAmount);
                });

                // 총 건수 및 총 금액 업데이트
                document.getElementById('total_count').innerText = totalCount;
                document.getElementById('total_amount').innerText = totalAmount.toLocaleString() + '원';
            } catch (error) {
                console.log('숙소 정보를 가져오는 중 오류 발생: ', error);
            }
        });
    } catch (error) {
        console.error('오류 발생:', error);
        alert('방 이름을 불러오는 중 오류가 발생했습니다: ' + error.message);
    }
});
