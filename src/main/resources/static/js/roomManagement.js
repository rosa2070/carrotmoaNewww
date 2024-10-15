document.addEventListener("DOMContentLoaded", function () {
    const userId = window.userId; // HTML에서 정의한 userId를 가져옴

    console.log(`Calling API with userId: ${userId}`);
    fetch(`/host/room/manage/${userId}`) // API 호출
        .then(response => {
            if (!response.ok) {
                throw new Error("네트워크 응답이 올바르지 않습니다.");
            }
            return response.json(); // JSON 데이터로 변환
        })
        .then(accommodations => {
            console.log(accommodations);
            const roomList = document.getElementById('roomList');
            roomList.innerHTML = ''; // 기존 내용을 지우고 새로 추가

            accommodations.forEach(accommodation => {
                const listItem = document.createElement('li');
                listItem.innerHTML = `
                    <dl class="room_item">
                        <dt>
                            <img src="${accommodation.imageUrl}" alt="방 이미지">
                        </dt>
                        <dd>
                            <p class="room_name ellipsis">
                                <a class="disable_preview">${accommodation.name}</a>
                            </p>
                            <p class="room_address">${accommodation.lotAddress} ${accommodation.detailAddress}</p>
                            <div class="room_pay">
                                <p>
                                    <strong>${accommodation.price}</strong><em>원</em>
                                    <span>/1박</span>
                                </p>
                            </div>
                        </dd>
                    </dl>
                    
                    <div class="drop_down">
                        <button type="button" class="btn_drop"><span>더보기</span></button>
                        <div class="drop_layer">
                            <ul class="btn_more">
                                <li class="icon_more_modify"><a btn-room-modify>수정하기</a></li>
                                <li class="icon_more_del"><a btn-room-delete>삭제하기</a></li>
                            </ul>
                        </div>
                    </div>
                `;
                roomList.appendChild(listItem);

                // 이벤트 리스너 추가
                const toggleButton = listItem.querySelector(".btn_drop");
                const dropDown = listItem.querySelector(".drop_layer");

                toggleButton.addEventListener("click", function () {
                    const isActive = dropDown.parentElement.classList.toggle("is_active");
                    dropDown.style.display = isActive ? "block" : "none"; // 드롭다운 보이기/숨기기
                });
            });
        })
        .catch(error => {
            console.error('숙소 정보를 가져오는 중 오류 발생: ', error);
        });
});
