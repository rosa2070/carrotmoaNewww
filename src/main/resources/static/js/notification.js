document.addEventListener("DOMContentLoaded", function() {
    const notificationIcon = document.getElementById("notificationIcon");
    const notificationModal = document.getElementById("notificationModal");
    const closeModal = document.querySelector(".notification-close");
    const notificationList = document.getElementById("notificationList");






    notificationIcon.addEventListener("click", function() {
        notificationModal.style.display = "block"; // 모달 보이기
        const eventSource = new EventSource(`/notifications${userId}`);
// 여기서 로그인되어있는 userId 전송하기
//         eventSource.onmessage = function (event) {




    });

    closeModal.addEventListener("click", function() {
        notificationModal.style.display = "none"; // 모달 숨기기
    });

});
