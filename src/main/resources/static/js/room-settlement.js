flatpickr("#s_start_date", {
    dateFormat: "Y-m-d", // 원하는 형식으로 설정 가능
});
flatpickr("#s_end_date", {
    dateFormat: "Y-m-d", // 원하는 형식으로 설정 가능
});

document.getElementById('btn_search').addEventListener('click', function () {
    const startDate = document.getElementById('s_start_date').value;
    const endDate = document.getElementById('s_end_date').value;
    const title = document.getElementById('opt_room_id').value; // 방 이름 선택(나중에 동적구현)

    fetch(`/api/host/room/settlement?title=${title}&startDate=${startDate}&endDate=${endDate}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("네트워크 응답이 올바르지 않습니다.");
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('settlement_table_body');
            tableBody.innerHTML = '';
            let totalCount = 0;
            let totalAmount = 0;

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

            document.getElementById('total_count').innerText = totalCount;
            document.getElementById('total_amount').innerText = totalAmount.toLocaleString() + '원';
        })
        .catch(error => {
            console.log('숙소 정보를 가져오는 중 오류 발생: ', error);
        });
});





