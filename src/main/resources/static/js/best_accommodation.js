// 페이지 로드 시 인기 호스트 방 목록을 가져와서 화면에 표시
window.onload = function() {
    fetch('/api/getPopularRooms')
        .then(response => response.json()) // JSON 형태로 응답 처리
        .then(rooms => {
            const container = document.getElementById('resultRoomContainer');
            rooms.forEach(room => {
                // 각 방의 HTML 구조를 생성
                const roomElement = document.createElement('a');
                roomElement.href = `/room/detail/${room.roomId}`;
                roomElement.target = "_blank"; // 새탭에서 열기

                roomElement.innerHTML = `
                    <dl class="room_item">
                        <dt>
                            <img class="room_img" src="https://example.com/room/${room.roomId}.png" alt="${room.roomName}">
                            <div class="group">
                                <span class="badge black">인기 호스트</span>
                            </div>
                        </dt>
                        <dd class="room_name">${room.roomName}</dd>
                        <dd class="room_address">${room.roomAddress}</dd>
                        <dd class="room_pay">
                            <p>
                                <strong>${room.contractCount * 10000}</strong><em>원</em>
                                <span>/1박</span>
                            </p>
                        </dd>
                    </dl>
                `;

                container.appendChild(roomElement); // 생성된 엘리먼트를 컨테이너에 추가
            });
        })
        .catch(error => {
            console.error('Error fetching popular rooms:', error);
        });
};