document.addEventListener('DOMContentLoaded', function () {
    const title = document.getElementById('room-info-title').textContent;
    const addr = document.getElementById('room-addr-info').textContent;
    const name = document.getElementById('host_name').textContent;
    const price = parseFloat(document.getElementById('room_info_price').textContent);
    const night = parseFloat(document.getElementById('nights-count').textContent, 10);
    const totalPrice = price * night;
    console.log(totalPrice);

    sessionStorage.setItem('room-info-title', title);
    sessionStorage.setItem('room-addr-info', addr);
    sessionStorage.setItem('host_name', name);
    sessionStorage.setItem('room_info_price', totalPrice);
})