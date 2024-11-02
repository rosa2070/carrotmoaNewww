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

        const result = await response.text();
        alert('결제가 취소되었습니다.');
        // UI 업데이트 등의 추가 작업 수행
    } catch (error) {
        console.error(error);
        alert('오류가 발생했습니다: ' + error.message);
    }
}
