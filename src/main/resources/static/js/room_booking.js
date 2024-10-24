document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        // YYYY년 MM월
        titleFormat: function (date) {
            return `${date.date.year}년 ${date.date.month + 1}월`;
        },
        selectable: true,
        dragScroll: true,
        validRange: {
            start: new Date().toISOString().split('T')[0]
        },
        select: function(info) {
            // 선택한 날짜 범위 정보
            var startDate = info.startStr;
            var endDate = new Date(info.endStr);
            endDate.setDate(endDate.getDate() - 1);
            var selectedEndDate = endDate.toISOString().split('T')[0];

            document.getElementById('checkin-dates').textContent =
                `${startDate}`;
            document.getElementById('checkout-dates').textContent =
                `${selectedEndDate}`;

            var timeDiffernece = selectedEndDate - startDate;
            var countNights = timeDiffernece / (1000 * 60 * 60 * 24) // 밀리초 단위에서 일로 변환해야함

            document.getElementById('nights-count').textContent = `총 ${countNights}박`;

            var pricePerWeek = document.getElementById('price').getAttribute('data-price');
            pricePerWeek = parseInt(pricePerWeek, 10);

            var totalPrice = (pricePerWeek / 7) * nights;
            document.getElementById('total-price').textContent = `${totalPrice.toLocaleString()}원`;


        }
    });
    calendar.render();
});

// // controller로 선택한 날짜 넘기기
// function sendSelectedDates(startDate, selectedEndDate) {
//     fetch('/your-controller-endpoint', {
//         method: 'Post',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify({
//             startDate: startDate,
//             endDate: selectedEndDate
//         })
//     })
//         .then(response => response.json())
//         .then(data => {
//             console.log('Success:', data);
//         })
//         .catch((error) => {
//             console.error('Error:', error);
//         });
// }