document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var reservationContainer = document.querySelector('.reservation-container');
    if (!reservationContainer) {
        console.error('reservation-container 요소를 찾을 수 없습니다.');
        return; // 요소가 없으면 함수 종료
    }
    var id = reservationContainer.getAttribute('data-accommodation-id');
    if (!id) {
        console.error('Accommodation ID를 찾을 수 없습니다.');
        return; // ID가 없으면 함수 종료
    }
    var today = new Date(); // 오늘
    var tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1);
    var sixMonthsLater = new Date(today.getFullYear(), today.getMonth() + 6, 1); // 6개월 후

    // 예약된 날짜 가져오기
    fetch(`/guest/booking/${id}`)
        .then(response => response.json())
        .then(data => {
            const events = data.map(booking => ({
                start: booking.checkInDate,
                end: booking.checkOutDate,
                display: 'background', // 배경으로 표시하여 예약 불가 처리
                color: '#FF6F0F', // 예약된 날짜의 배경색
                // classNames: ['booked']
                // selectable: false, // 이벤트의 속성으로 사용할 수 업다
                // dragScroll: false,
            }));

            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                // YYYY년 MM월
                titleFormat: function (date) {
                    return `${date.date.year}년 ${date.date.month + 1}월`;
                },
                selectable: true,
                dragScroll: true,
                validRange: {
                    start: tomorrow.toISOString().split('T')[0], // 오늘부터 예약 가능
                    end: sixMonthsLater.toISOString().split('T')[0] // 6개월까지만 예약 가능
                },
                events: events, // 예약된 날짜를 events로 추가
                select: function(info) {
                    // 하루만 선택하는 것도 불가능하게
                    if(info.start === info.end) {
                        alert('하루는 선택할 수 없습니다. 최소 1박 이상 선택해주세요');
                        calendar.unselect();
                        return;
                    }

                    // 이미 예약이 있는 날과 겹치면 X, 예약 마지막 날은 겹치는 거 가능
                    const isBlocked = data.some(event =>
                        (info.start < new Date(event.checkOutDate) && info.end > new Date(event.checkInDate)) && !(info.end === new Date(event.checkOutDate))
                        );
                    if (isBlocked) {
                        alert('이 날짜는 예약할 수 없습니다.');
                        calendar.unselect(); // 선택을 취소
                        return;
                    }

                    // 선택한 날짜 범위 정보
                    var startDate = info.startStr;
                    var endDate = new Date(info.endStr);
                    endDate.setDate(endDate.getDate() - 1);
                    var selectedEndDate = endDate.toISOString().split('T')[0];

                    document.getElementById('checkin-dates').textContent = startDate;
                    document.getElementById('checkout-dates').textContent = selectedEndDate;

                    var timeDifference = endDate - new Date(info.startStr);
                    var countNights = ((timeDifference) / (1000 * 60 * 60 * 24)) + 1;

                    document.getElementById('nights-count').textContent = countNights;

                },
                // eventDidMount: function(info) {
                //     // 배경으로 표시된 예약된 날짜에 대해 마우스 이벤트 비활성화
                //     if (info.event.display === 'background') {
                //         info.el.style.pointerEvents = 'none'; // 마우스 이벤트 비활성화
                //     }
                // }
            });
            calendar.render();
        })
    .catch(error => console.error('Error fetching bookings:', error));
});