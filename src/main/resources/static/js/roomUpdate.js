document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('form').addEventListener('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출 방지

        const accommodationData = {
            title: document.getElementById('name').value,
            totalArea: parseInt(document.getElementById('totalArea').value, 10),
            roadAddress: document.getElementById('roadAddress').value,
            lotAddress: document.getElementById('lotAddress').value,
            detailAddress: document.getElementById('detailAddress').value,
            floor: parseInt(document.getElementById('floor').value, 10),
            totalFloor: parseInt(document.getElementById('totalFloor').value, 10),
            price: document.getElementById('price').value,
            content: document.getElementById('detail').value,
            transportationInfo: document.getElementById('transportationInfo').value,
            imageUrls: Array.from(document.querySelectorAll('#div_added_pictures img')).map(img => img.src),
            amenityIds: Array.from(document.querySelectorAll('input[name="amenityIds"]:checked')).map(checkbox => checkbox.value),
            spaceCounts: [
                parseInt(document.getElementById('room_cnt').value, 10),
                parseInt(document.getElementById('bathroom_cnt').value, 10),
                parseInt(document.getElementById('sittingroom_cnt').value, 10),
                parseInt(document.getElementById('cookroom_cnt').value, 10)
            ]
        };

        fetch('/host/room/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(accommodationData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('성공:', data);
            })
            .catch((error) => {
                console.error('오류:', error);
            });
    });
});