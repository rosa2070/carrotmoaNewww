package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.ChatMessage;
import carrotmoa.carrotmoa.entity.ChatRoom;
import carrotmoa.carrotmoa.model.request.ChatMessageRequest;
import carrotmoa.carrotmoa.model.request.ChatRoomRequest;
import carrotmoa.carrotmoa.repository.ChatMessageRepository;
import carrotmoa.carrotmoa.repository.ChatRoomRepository;
import carrotmoa.carrotmoa.repository.ChatRoomUserRepository;
import carrotmoa.carrotmoa.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final String destination = "/chat/sub/";

    private final SimpMessagingTemplate template;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public ChatMessageRequest sendMessage(ChatMessageRequest message) {
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).orElseThrow( ()-> new EntityNotFoundException("해당 채팅방을 찾을 수 없습니다. : " + message.getChatRoomId()));
        template.convertAndSend(destination + message.getChatRoomId(), message);
        chatMessageRepository.save(message.toEntityChatMessage());
        return message;
    }

    public List<ChatRoomRequest> getAllChatRooms(long userId){
        System.out.println("getAllChatRooms 호출");
        return chatRoomUserRepository.findByUserId(userId)
        .stream().map(entity -> chatRoomRepository.findById(entity.getChatRoomId()))
                .filter(Optional::isPresent)
                .map(room -> new ChatRoomRequest(room.get()))
                .collect(Collectors.toList());
    }
    /*메시지를 보낸 유저의 닉네임을 messageEntity에 추가함
     UserProfile의 닉네임과 값을 똑같이 유지할 방법 생각중
        applicationEnvent , trigger
    */
    public List<ChatMessageRequest> getChatMessage(long chatRoomId){
        return chatMessageRepository.findByChatRoomId(chatRoomId)
                .stream().map(ChatMessageRequest::new).toList();
    }



}
