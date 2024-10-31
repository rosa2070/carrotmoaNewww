window.addEventListener("load", function () {
    const start = sessionStorage.getItem("checkin-dates");
    const end = sessionStorage.getItem("checkout-dates");
    if(start && end){
        document.getElementById("checkin-dates").textContent = start;
        document.getElementById("checkout-dates").textContent = end;
    }
})