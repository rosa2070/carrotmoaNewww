// 문서가 로드된 후에 이벤트 리스너를 추가합니다.
document.addEventListener('DOMContentLoaded', () => {
    const cancelButton = document.getElementById('btn-cancel-booking-popup'); // ID로 버튼 선택

    if (cancelButton) {
        cancelButton.addEventListener('click', () => {
            const impUid = cancelButton.getAttribute('data-imp-uid'); // data-imp-uid에서 impUid 가져오기
            console.log('impUid:', impUid); // impUid 값 로그 출력
            cancelBooking(impUid); // cancelBooking 함수 호출
        });
    }
});

// cancelBooking 함수 정의
async function cancelBooking(impUid) {
    try {
        const response = await fetch(`/api/payment/cancel/${impUid}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('결제 취소에 실패했습니다.');
        }

        const result = await response.text(); // 문자열로 응답 받기
        alert(result); // 성공 메시지 알림


        // UI 업데이트: 완료된 계약 항목 제거
        // const contractItem = cancelButton.closest('.contract-item'); // 취소 버튼의 부모 요소 찾기
        // if (contractItem) {
        //     contractItem.remove(); // 해당 요소 제거
        // }

        // UI 업데이트: 취소된 계약 항목 추가
        // const canceledContractsSection = document.querySelector('h4 + div'); // "취소된 계약" 섹션 선택
        //
        // const newCanceledItem = document.createElement('div');
        // newCanceledItem.classList.add('contract-item');
        // newCanceledItem.setAttribute('th:each', 'booking : ${bookings}');
        // newCanceledItem.setAttribute('th:if', "booking.status == 2");
        // newCanceledItem.innerHTML = `
        //     <div class="contract_header">
        //         <div class="flex contract_info" style="cursor:pointer;" th:onclick="@{/guest/room/detail/{id}(id=${canceledBooking.accommodationId})}">
        //             <a th:href="@{/room/detail/{id}(id=${canceledBooking.accommodationId})}" style="text-decoration: none; color: inherit;">
        //                 <span class="badge gray">계약 취소</span>
        //                 <p class="room_title" th:text="${canceledBooking.title}">${canceledBooking.title}</p>
        //             </a>
        //         </div>
        //     </div>
        //     <div class="contract_cont" style="align-items:center">
        //         <dl class="room_item" style="cursor:pointer;" th:onclick="@{/guest/room/detail/{id}(id=${canceledBooking.accommodationId})}">
        //             <dt>
        //                 <img th:src="${canceledBooking.imageUrl}" src="${canceledBooking.imageUrl}" alt="Room Image">
        //             </dt>
        //             <dd>
        //                 <div class="room_address_ellipsis" th:text="${canceledBooking.lotAddress} + ${canceledBooking.detailAddress} + ${canceledBooking.floor}">${canceledBooking.lotAddress} ${canceledBooking.detailAddress} ${canceledBooking.floor}</div>
        //                 <p class="room_period" th:text="${canceledBooking.checkInDate} + '~' + ${canceledBooking.checkOutDate}">${canceledBooking.checkInDate} ~ ${canceledBooking.checkOutDate}</p>
        //             </dd>
        //         </dl>
        //         <strong class="room.pay" th:text="${canceledBooking.totalPrice}">${canceledBooking.totalPrice}</strong>
        //         <button type="button" class="btn orange" btn-open-review-popup>재예약</button>
        //     </div>
        // `;
        //
        // canceledContractsSection.appendChild(newCanceledItem); // 취소된 계약 섹션에 새 항목 추가














    } catch (error) {
        console.error(error);
        alert('오류가 발생했습니다: ' + error.message);
    }
}
