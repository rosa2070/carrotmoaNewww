//userId 안들어감
window.onload = function () {
    if (document.getElementById('chat-modal-open')) {
        document.getElementById('chat-modal-open').addEventListener('click', function () {
            const modal = document.getElementById("chat-modal-content");
            const closeModalButtonId = 'chat-close-modal';

            fetch('/user/modal-content')
                .then(response => response.text())
                .then(data => {
                    modal.innerHTML = data; // 받아온 내용을 모달에 추가
                    modal.style.display = "flex"; // 모달 열기
                    const userId = document.getElementById('userId').value;
                    const inputMessage = document.getElementById('input-message');
                    findChatRoom(userId);

                    // 닫기 버튼 이벤트 추가
                    const closeModalButton = document.getElementById(closeModalButtonId);
                    closeModalButton.onclick = function () {
                        modal.style.display = "none"; // 모달 닫기
                    }



                    let stompClient = null;
                    let chatRoomId = 0;

                    function connect(chatRoomId) {
                        console.log(chatRoomId)
                        const socket = new SockJS('/chat');
                        stompClient = Stomp.over(socket);
                        stompClient.connect({}, function (frame) {
                            console.log('Connected: ' + frame);
                            stompClient.subscribe('/sub/chat/' + chatRoomId, function (message) {
                                messageDiv(JSON.parse(message.body));
                                // showMessage(JSON.parse(message.body));
                            });
                        });
                    }


                    const chatMessage = document.getElementById('chat-messages');

                    //사용자가 들어가있는 채팅방 목록 호출
                    // document.getElementById("get-chat-room-list").addEventListener('click', function () {
                    //     findChatRoom(userId);
                    // })
                    //사용자가 선택한 채팅방 채팅목록 호출
                    document.getElementById('send-message-btn').addEventListener('click', function () {
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
                        stompClient.send('/pub/chat/' + chatRoomId, {}, JSON.stringify(data));
                        inputMessage.value = "";
                    }

                    //선택한 채팅방 입장   -> websocket 구독 , db에서 채팅 목록 가져오기
                    function getChatMessage(roomId) {
                        chatRoomId = roomId;
                        console.log(chatRoomId);
                        connect(chatRoomId);

                        fetch("/api/chat/get-message/" + roomId, {
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
                                const chatMessages = document.getElementById('chat-messages');
                                chatMessages.scrollTop = chatMessages.scrollHeight;

                            })
                    }

                    function findChatRoom(userId) {
                        console.log("메서드 호출")
                        fetch("/api/chat/all-Room/" + userId, {
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

                    function messageDiv(message) {
                        const chatMessageElement = document.createElement("div");
                        const chatMessageTitleElement = document.createElement("p")
                        const chatMessageContentElement = document.createElement("input");
                        const ChatMessageSendTimeElement = document.createElement('div')
                        chatMessageContentElement.type = 'text'; // 타입을 text로 설정
                        chatMessageContentElement.readOnly = true; // 수정 불가능하게 설정
                        chatMessageContentElement.value = message.message; // 초기 값 설정

                        chatMessageTitleElement.textContent = message.nickname;
                        let createdAt = message.createdAt.split('T');
                        let createdAtDate = createdAt[0];
                        let createdAtTime = createdAt[1].split(':');
                        let morningAndAfternoon;
                        let hour = createdAtTime[0];
                        let minute = createdAtTime[1];
                        if (createdAtTime[0] - 11 >= 1) {
                            morningAndAfternoon = '오후'
                            hour = createdAtTime[0] - 12;
                        } else {
                            morningAndAfternoon = '오전'
                        }

                        let dateFormat = morningAndAfternoon + hour + ":" + minute;

                        ChatMessageSendTimeElement.textContent = dateFormat;
                        chatMessageElement.appendChild(chatMessageTitleElement);
                        chatMessageElement.appendChild(chatMessageContentElement);
                        chatMessageElement.appendChild(ChatMessageSendTimeElement);

                        chatMessage.appendChild(chatMessageElement);

                    }

                    document.getElementById('chat-find-user').addEventListener('click', function () {
                        let searchNickname = document.getElementById('chat-find-user-search').value;
                        chatFindUser(searchNickname);
                    });

                    function chatFindUser(searchKeyword) {
                        console.log(searchKeyword);
                        fetch(`/api/user/find-user?searchType=nickname&searchKeyword=${encodeURIComponent(searchKeyword)}`, {
                            method: "GET",
                            headers: {
                                'Content-Type': 'application/json'
                            },
                        })
                            .then(response => {
                                if (!response.ok) {

                                    console.log("찾을 수 없음")
                                }
                                return response.json()
                            })
                            .then(result => {
                                if (result.userId == null) {
                                    alert('해당 유저 없음');
                                }
                                console.log("회원번호 : " + result.userId);
                                console.log("닉네임 : " + result.nickname);
                                document.getElementById('chat-find-result-id').value = result.userId;
                                document.getElementById('chat-find-result-nickname').value = result.nickname;

                            });
                    }

                    document.getElementById('chat-room-create').addEventListener('click', function () {
                        let joinTargetUserId = document.getElementById('chat-find-result-id').value;
                        createChatRoom(userId, joinTargetUserId);
                    })

                    function createChatRoom(myUserId, joinTargetUserId) {
                        console.log(myUserId);
                        console.log(joinTargetUserId);
                        if(stompClient && stompClient.connected) {
                            stompClient.disconnect(function () {
                                console.log('Disconnected from WebSocket.');
                            });
                            fetch(`/api/chat/create-room?myUserId=${encodeURIComponent(myUserId)}&joinTargetUserId=${encodeURIComponent(joinTargetUserId)}`, {
                                method: 'GET',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                            })
                                .then(response => {
                                    if (!response.ok) {
                                        console.log("채팅방 생성 실패")
                                    }
                                    return response.json()
                                })
                                .then(result => {
                                    console.log('채팅방 생성 , 입장');
                                    chatRoomId = result;
                                    connect(result);
                                    getChatMessage(chatRoomId);
                                });
                        }

                    }
                })
                .catch(error => {
                    console.error('모달 내용을 가져오는 데 오류가 발생했습니다:', error);
                });

        })
    }

}

