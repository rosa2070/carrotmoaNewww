document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var today = new Date(); // 오늘
    var sixMonthsLater = new Date(today.getFullYear(), today.getMonth() + 6, 1); // 6개월 후
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        // YYYY년 MM월
        titleFormat: function (date) {
            return `${date.date.year}년 ${date.date.month + 1}월`;
        },
        selectable: true,
        dragScroll: true,
        validRange: {
            start: today.toISOString().split('T')[0], // 오늘부터 예약 가능
            end: sixMonthsLater.toISOString().split('T')[0] // 6개월까지만 예약 가능
        },
        select: function(info) {
            // 사용자가 선택을 다시 하는 경우 이전 선택된 날짜 초기화
            document.getElementById('checkin-dates').textContent = '';
            document.getElementById('checkout-dates').textContent = '';
            document.getElementById('count_nights').textContent = '';
            document.getElementById('total-price').textContent = '';

            // 선택한 날짜 범위 정보
            var startDate = info.startStr;
            var endDate = new Date(info.endStr);
            endDate.setDate(endDate.getDate() - 1);
            var selectedEndDate = endDate.toISOString().split('T')[0];

            document.getElementById('checkin-dates').textContent = startDate;
                // `${startDate}`;
            document.getElementById('checkout-dates').textContent = selectedEndDate;
                // `${selectedEndDate}`;

            var timeDiffernece = selectedEndDate - startDate;
            var countNights = timeDiffernece / (1000 * 60 * 60 * 24) // 밀리초 단위에서 일로 변환해야함

            document.getElementById('nights-count').textContent = countNights;

            var pricePerWeek = document.getElementById('price').getAttribute('data-price');
            pricePerWeek = parseInt(pricePerWeek, 10);

            var totalPrice = (pricePerWeek / 7) * countNights;
            document.getElementById('total-price').textContent = totalPrice.toLocaleString();
        },
        unselected:function () {
            document.getElementById('checkin-dates').textContent = '';
            document.getElementById('checkout-dates').textContent = '';
            document.getElementById('nights-count').textContent = '';
            document.getElementById('total-price').textContent = '';
        }
    });
    calendar.render();
});