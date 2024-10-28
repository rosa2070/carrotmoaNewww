// document.addEventListener('DOMContentLoaded', function() {
//     var calendarEl = document.getElementById('calendar');
//     var today = new Date(); // 오늘
//     var tomorrow = new Date(today);
//     tomorrow.setDate(today.getDate() + 1);
//     var sixMonthsLater = new Date(today.getFullYear(), today.getMonth() + 6, 1); // 6개월 후
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//         initialView: 'dayGridMonth',
//         // YYYY년 MM월
//         titleFormat: function (date) {
//             return `${date.date.year}년 ${date.date.month + 1}월`;
//         },
//
//         selectable: true,
//         dragScroll: true,
//         validRange: {
//             start: tomorrow.toISOString().split('T')[0], // 오늘부터 예약 가능
//             end: sixMonthsLater.toISOString().split('T')[0] // 6개월까지만 예약 가능
//         },
//
//         select: function(info) {
//             // 사용자가 선택을 다시 하는 경우 이전 선택된 날짜 초기화
//             // document.getElementById('checkin-dates').textContent = '';
//             // document.getElementById('checkout-dates').textContent = '';
//             // document.getElementById('count_nights').textContent = '';
//             // document.getElementById('total-price').textContent = '';
//
//             // 선택한 날짜 범위 정보
//             var startDate = info.startStr;
//             var endDate = new Date(info.endStr);
//             endDate.setDate(endDate.getDate() - 1);
//             var selectedEndDate = endDate.toISOString().split('T')[0];
//
//             document.getElementById('checkin-dates').textContent = startDate;
//             document.getElementById('checkout-dates').textContent = selectedEndDate;
//
//             var timeDiffernece = endDate - new Date(info.startStr);
//             var countNights = ((timeDiffernece) / (1000 * 60 * 60 * 24)) + 1; // 밀리초 단위에서 일로 변환해야함
//
//             document.getElementById('nights-count').textContent = countNights;
//
//             var pricePerWeek = document.getElementById('price').getAttribute('data-price');
//             pricePerWeek = parseInt(pricePerWeek, 10);
//
//             var totalPrice = (pricePerWeek / 7) * countNights;
//             document.getElementById('total-price').textContent = totalPrice.toLocaleString();
//         },
//         // unselected:function () {
//         //     document.getElementById('checkin-dates').textContent = '';
//         //     document.getElementById('checkout-dates').textContent = '';
//         //     document.getElementById('nights-count').textContent = '';
//         //     document.getElementById('total-price').textContent = '';
//         // }
//     });
//     calendar.render();
// });

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var reservationContainer = document.querySelector('.reservation-container');
    if (!reservationContainer) {
        console.error('reservation-container 요소를 찾을 수 없습니다.');
        return; // 요소가 없으면 함수 종료
    }
    var id = reservationContainer.getAttribute('data-accommodation-id');
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
                color: '#FF6F0F' // 예약된 날짜의 배경색
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
                events: data, // 예약된 날짜를 events로 추가
                select: function(info) {
                    // 예약된 날짜 확인
                    const isBlocked = data.some(event =>
                        (info.start >= new Date(event.start) && info.start <= new Date(event.end)) ||
                        (info.end >= new Date(event.start) && info.end <= new Date(event.end))
                    );

                    if (isBlocked) {
                        alert('이 날짜는 예약할 수 없습니다.');
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

                }
            });
            calendar.render();
        })
    .catch(error => console.error('Error fetching bookings:', error));
});