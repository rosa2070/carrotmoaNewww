document.getElementById('user-update').addEventListener('submit',function(updateEvent){
    alert('function 호출');
    updateEvent.preventDefault();
    const formData = new FormData(this);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    })
    fetch("/api/user/update", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                console.log("값 안옴")
            }
            return response.json()
        })

        .then(result => {
            if (result) {
                alert('정상');
                window.location.href = "/user/my-page";
            } else {
                console.log("비정상");
            }
        });
});