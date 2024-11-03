document.getElementById("likeButton").addEventListener("click", function() {
    const button = this;
    const accommodationId = button.getAttribute("data-accommodation-id");

    // 버튼의 active 상태 토글
    button.classList.toggle("is_active");

    // AJAX 요청 보내기
    fetch("/wishList/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            "id": accommodationId
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('error');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            // 필요하다면 추가 처리
        })
        .catch((error) => {
            console.error('Error:', error);
            // 에러 처리
        });
});