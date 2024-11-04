package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.entity.ChatRoom;
import carrotmoa.carrotmoa.model.request.ChatMessageRequest;
import carrotmoa.carrotmoa.model.request.ChatRoomRequest;
import carrotmoa.carrotmoa.repository.ChatRoomUserRepository;
import carrotmoa.carrotmoa.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatRoomUserRepository chatRoomUserRepository;
    ChatService chatService;
        public ChatController(ChatService chatService, ChatRoomUserRepository chatRoomUserRepository) {
            this.chatService = chatService;
            this.chatRoomUserRepository = chatRoomUserRepository;
        }


    //사용자가 사용중인 모든 채팅방 검색
    @GetMapping("/all-Room/{userId}")
    public ResponseEntity<List<ChatRoomRequest>> getChatMessages(@PathVariable long userId){
        return new ResponseEntity<>(chatService.getAllChatRooms(userId), HttpStatus.OK);
    }

    @GetMapping("/get-message/{chatRoomId}")
    public ResponseEntity<List<ChatMessageRequest>> getChatMessage(@PathVariable long chatRoomId){
        return new ResponseEntity<>(chatService.getChatMessage(chatRoomId), HttpStatus.OK);
    }
    @MessageMapping("/chat/{chatRoomId}")
    @SendTo("/sub/chat/{chatRoomId}")
    public ChatMessageRequest sendMessage(@DestinationVariable long chatRoomId,@Payload ChatMessageRequest chatMessageRequest){
            return chatService.sendMessage(chatMessageRequest);
    }
    //채팅방 생성
    @GetMapping("/create-room")
    public ResponseEntity<Long> createChatRoom(@RequestParam long myUserId, @RequestParam long joinTargetUserId){
            return new ResponseEntity<>(chatService.createChatRoom(myUserId,joinTargetUserId), HttpStatus.OK);
    }
}
