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
    var checkInDate = null;
    var checkOutDate = null;
    var bookedDates = [];

    // 예약된 날짜 가져오기
    fetch(`/guest/booking/${id}`)
        .then(response => response.json())
        .then(data => {
            console.log(data)
            const events = data.map(booking => ({
                start: booking.checkInDate,
                end: booking.checkOutDate,
                display: 'background', // 배경으로 표시하여 예약 불가 처리
                color: '#FF6F0F', // 예약된 날짜의 배경색
            }));

            bookedDates = data.map(booking => ({
                start: new Date(booking.checkInDate),
                end: new Date(booking.checkOutDate)
            }))

            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                // YYYY년 MM월
                titleFormat: function (date) {
                    return `${date.date.year}년 ${date.date.month + 1}월`;
                },
                selectable: true,
                selectAllow: function (info) {
                    var selectedStart = info.start;
                    var selectedEnd = info.end;

                    return !bookedDates.some(booked => {
                        var bookedEndPermit = new Date(booked.end);
                        bookedEndPermit.setDate(bookedEndPermit.getDate() - 1);

                        return (selectedStart < bookedEndPermit && selectedEnd > booked.start);
                    });
                },
                // dragScroll: true,
                validRange: {
                    start: tomorrow.toISOString().split('T')[0], // 오늘부터 예약 가능
                    end: sixMonthsLater.toISOString().split('T')[0] // 6개월까지만 예약 가능
                },
                events: events, // 예약된 날짜를 events로 추가
                select: function(info) {
                    // 하루만 선택하는 것도 불가능하게
                    // const newEndDate = new Date(info.end.getFullYear(), info.end.getMonth(), info.end.getDate());
                    // newEndDate.setDate(info.end.getDate() - 1);
                    // console.log(newEndDate);

                    if(!checkInDate) {
                        checkInDate = info.start;
                        calendar.addEvent({
                            start: checkInDate,
                            end: new Date(checkInDate.getTime() + 86400000),
                            display: 'background',
                            color: '#FF6F0F'
                        });
                    } else if (!checkOutDate) {
                        checkOutDate = info.start;

                        if(checkOutDate <= checkInDate) {
                            alert('1박 이상 예약이 가능합니다');
                            calendar.unselect();
                            return;
                        }
                        calendar.addEvent({
                            start: checkInDate,
                            end: new Date(checkInDate.getTime() + 86400000),
                            display: 'background',
                            color: '#FF6F0F'
                        });
                        document.getElementById('checkin-dates').textContent = checkInDate.toISOString().split('T')[0];
                        document.getElementById('checkout-dates').textContent = checkOutDate.toISOString().split('T')[0];

                        var timeDifference = checkOutDate - checkInDate;
                        var countNights = ((timeDifference) / (1000 * 60 * 60 * 24)) + 1;
                        document.getElementById('nights-count').textContent = countNights;

                    }  else {
                        checkInDate = null;
                        checkOutDate = null;
                        calendar.getEvents().forEach(event => {
                            if (event.display == 'background' && event.color == '#FF6F0F') {
                                event.remove();
                            }
                        });
                    }

                    // // 선택한 날짜 범위 정보
                    // var startDate = info.startStr;
                    // var endDate = new Date(info.endStr);
                    // endDate.setDate(endDate.getDate() - 1);
                    // var selectedEndDate = endDate.toISOString().split('T')[0];
                    //
                    // document.getElementById('checkin-dates').textContent = startDate;
                    // document.getElementById('checkout-dates').textContent = selectedEndDate;



                },
                unselect: function() {
                    if (!checkOutDate) {
                        checkInDate = null;
                        calendar.getEvents().forEach(event => {
                            if (event.display == 'background' && event.color == '#FF6F0F') {
                                event.remove();
                            }
                        });
                    }
                }
            });
            calendar.render();
        })
    .catch(error => console.error('Error fetching bookings:', error));
});