const notificationIcon = document.getElementById("notificationIcon");
const notificationModal = document.getElementById("notificationModal");
const notificationCloseModal = document.querySelector(".notification-close");
const notificationList = document.getElementById("notificationList");
const notificationToast = document.getElementById("notificationToast");
document.addEventListener("DOMContentLoaded", function () {

    notificationIcon.addEventListener("click", function () {
        notificationModal.style.display = "block"; // 모달 보이기
        notificationIcon.querySelector("img").src = "/images/notification.svg"; // 기본 아이콘으로 복원
    });

    notificationCloseModal.addEventListener("click", function () {
        notificationModal.style.display = "none"; // 모달 숨기기
    });

    fetchLoginUserNotifications();


});


document.addEventListener("DOMContentLoaded", function () {
// login이 된 상태면 -> SSE 연결하기.
    if (userObject) {
        const userId = userObject.userProfile.userId;
        const sse = new EventSource(`/sse/notifications/${userId}`);


        sse.onopen = function () {
            console.log('SSE 연결이 성공적으로 설정되었습니다.');
        };

        sse.onmessage = function (event) {
            const notification = event.data;
            console.log('새로운 알림:', notification);
            // 알림 UI 업데이트 로직 추가
            updateNotificationIcon();
            showNotificationToast();
        };

        sse.onerror = function (err) {
            console.error('SSE 연결 오류:', err);
        };
        // 이벤트는 이름이고 이벤트.data로해서 값을 불러오는듯.
        // 작성해준 이벤트의 이름(connect)은 클라이언트가 이벤트를 불러올 때 사용!!!
        sse.addEventListener("connect", function (e) {
            let datatest = e.data;
            console.log("더미데이터 값을 받아서 사용이 가능할까? -> ", datatest)
        })

    } else {
        console.log('로그인되지 않은 사용자입니다.');
    }

});

function updateNotificationIcon() {
    // 알림 아이콘을 변경
    notificationIcon.querySelector("img").src = "/images/notification-dot.svg"; // 기본 아이콘으로 복원
}

function showNotificationToast() {
    notificationToast.style.display = "block"; // 모달 보이기
    setTimeout(() => {
        notificationToast.style.opacity = 1; // 불투명도 설정
        notificationToast.style.transform = "translateY(0)"; // 기본 위치로 이동
    }, 10); // 약간의 지연 후 애니메이션 시작

    // 10초 후에 자동으로 사라지게 설정
    setTimeout(function () {
        notificationToast.style.opacity = 0; // 사라짐 효과
        setTimeout(function () {
            notificationToast.style.display = "none"; // 완전히 숨기기
        }, 500); // 사라짐 효과가 끝난 후 숨김
    }, 5000); // 5초 후
}

// 알림 데이터를 가져오는 함수
function fetchLoginUserNotifications() {
    const receiverId = userObject.userProfile.userId; // 로그인한 사용자 ID
    fetch(`/api/notifications/${receiverId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json(); // JSON으로 변환
        })
        .then(data => {
            // 알림 목록을 업데이트
            console.log(data);
            updateNotificationList(data);
        })
        .catch(error => {
            console.error('Fetch 오류:', error);
        });
}


    // 알림 목록을 업데이트하는 함수
    function updateNotificationList(notifications) {
        notificationList.innerHTML = ""; // 기존 알림 목록 초기화
        //
            notifications.forEach(notification => {
            const notificationEntry = document.createElement("div");
            notificationEntry.className = "notification-entry";
            const notificationClass = notification.read ? 'notification-url read' : 'notification-url';
            notificationEntry.innerHTML = `
            <a class="${notificationClass}" href="${notification.url}">
                <div class="notification-user-pic" >
                    <img src="${notification.picUrl}" alt="유저 프로필 이미지">
                </div>
                <div class="notification-details">
                            <div class="notification-title">  ${notification.title} </div>
                    <div class="notification-message">
                           ${notification.userName}님: ${notification.message}</div>
                    <div class="notification-createdAt">${new Date(notification.createdAt).toLocaleString()}</div>
                </div>
            </a>
            `;
            notificationList.appendChild(notificationEntry); // 알림 목록에 추가
        });
            }