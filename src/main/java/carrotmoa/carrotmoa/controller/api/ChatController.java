package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.entity.ChatRoom;
import carrotmoa.carrotmoa.model.request.ChatMessageRequest;
import carrotmoa.carrotmoa.model.request.ChatRoomRequest;
import carrotmoa.carrotmoa.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {
        ChatService chatService;
        public ChatController(ChatService chatService) {
            this.chatService = chatService;
        }


    //사용자가 사용중인 모든 채팅방 검색
    @GetMapping("/chat/all-Room/{userId}")
    public ResponseEntity<List<ChatRoomRequest>> getChatMessages(@PathVariable long userId){
        System.out.println("ChatController 호출");
        return new ResponseEntity<>(chatService.getAllChatRooms(userId), HttpStatus.OK);
    }

    @GetMapping("/chat/get-message/{chatRoomId}")
    public ResponseEntity<List<ChatMessageRequest>> getChatMessage(@PathVariable long chatRoomId){
        return new ResponseEntity<>(chatService.getChatMessage(chatRoomId), HttpStatus.OK);
    }
    @MessageMapping("/chat/{chatRoomId}")
    @SendTo("/sub/chat/{chatRoomId}")
    public ChatMessageRequest sendMessage(@DestinationVariable long chatRoomId,@Payload ChatMessageRequest chatMessageRequest){
        System.out.println("send-message 호출 : " + chatRoomId);
            return chatService.sendMessage(chatMessageRequest);
    }

}
