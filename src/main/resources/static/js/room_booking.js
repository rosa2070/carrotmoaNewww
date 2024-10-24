document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        // YYYY년 MM월
        titleFormat: function (date) {
            return `${date.date.year}년 ${date.date.month + 1}월`;
        },

    });
    calendar.render();
});