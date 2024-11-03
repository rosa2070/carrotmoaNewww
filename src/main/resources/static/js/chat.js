//userId 안들어감
window.onload = function () {
    const  userId = document.getElementById('userId').value;
    const inputMessage = document.getElementById('input-message');

    let stompClient = null;
    let chatRoomId = 0;
    function connect(chatRoomId) {
        console.log(chatRoomId)
        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/sub/chat/'+chatRoomId, function (message) {
                debugger;
                messageDiv(JSON.parse(message.body));
                // showMessage(JSON.parse(message.body));
            });
        });
    }


//test
    const chatMessage = document.getElementById('chat-messages');

    //사용자가 들어가있는 채팅방 목록 호출
    document.getElementById("get-chat-room-list").addEventListener('click',function(){
        findChatRoom(userId);
    })
    //사용자가 선택한 채팅방 채팅목록 호출
    document.getElementById('send-message-btn').addEventListener('click',function(){
       sendMessage();
    });
    
    function sendMessage() {
        document.getElementById('chat-room-id').value = chatRoomId;
        console.log(document.getElementById('chat-room-id').value);
        const formData = new FormData(document.getElementById('chat-form'));

        const data = {};
        formData.forEach((value, key) => {
            data[key] = value;
        });
        stompClient.send('/pub/chat/'+chatRoomId, {}, JSON.stringify(data));
        inputMessage.value = "";
    }
    //선택한 채팅방 입장   -> websocket 구독 , db에서 채팅 목록 가져오기
    function getChatMessage(roomId) {
        chatRoomId = roomId;
       console.log(chatRoomId);
       connect(chatRoomId);

        fetch("/chat/get-message/" + roomId, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => {
                if (!response.ok) {
                    alert("에러");
                }
                return response.json();
            })
            .then(result => {
                    chatMessage.innerHTML = '';
                // document.getElementById('chat-room-id').value = result[0].chatRoomId;
                console.log(result[0].chatRoomId);
                result.forEach(message => {

                    messageDiv(message);

                })

            })
    }
    function findChatRoom(userId) {
        console.log("메서드 호출")
        fetch("/chat/all-Room/" + userId, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => {
                if (!response.ok) {
                    console.log("에러")
                }
                return response.json()
            })
            .then(result => {
                const chatRoomList = document.getElementById('chat-room-list');
                chatRoomList.innerHTML = '';

                result.forEach(room => {
                    const roomElement = document.createElement("div");
                    const roomLinkElement = document.createElement("a")
                    const roomNameElement = document.createElement("p")
                    roomElement.classList.add('chat-room-item');
                    // roomElement.id = "chat-room-item";
                    roomLinkElement.classList.add('chat-room-item-link');
                    roomLinkElement.onclick = function () {
                        getChatMessage(room.roomId);
                    }

                    roomElement.appendChild(roomLinkElement);

                    roomNameElement.classList.add('chat-room-item-title')
                    roomNameElement.textContent = room.roomName;
                    roomLinkElement.appendChild(roomNameElement);
                    chatRoomList.appendChild(roomElement);
                });
            });
    }
    function messageDiv(message){
        const chatMessageElement = document.createElement("div");
        const chatMessageTitleElement = document.createElement("p")
        const chatMessageContentElement = document.createElement("input");
        chatMessageContentElement.type = 'text'; // 타입을 text로 설정
        chatMessageContentElement.readOnly = true; // 수정 불가능하게 설정
        chatMessageContentElement.value = message.message; // 초기 값 설정

        chatMessageTitleElement.textContent = message.nickname;
        chatMessageElement.appendChild(chatMessageTitleElement);
        chatMessageElement.appendChild(chatMessageContentElement);

        chatMessage.appendChild(chatMessageElement);

    }
    document.getElementById('chat-find-user').addEventListener('click',function(){
        let searchNickname = document.getElementById('chat-find-user-search').value;
       chatFindUser(searchNickname);
    });
    function chatFindUser(searchKeyword){
        console.log(searchKeyword);
        fetch(`api/user/find-user?type=nickname&searchKeyword=${encodeURIComponent(searchKeyword)}`,{
        method: "GET",
            headers: {
            'Content-Type': 'application/json'
        },
    })
.then(response => {
        if (!response.ok) {
            console.log("에러")
        }
        return response.json()
    })
        .then(result => {
            console.log(result.nickname);
            console.log(result.userId);
        });
    }


}

