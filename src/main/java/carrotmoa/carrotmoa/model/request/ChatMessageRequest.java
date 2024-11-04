package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.config.security.CustomUserDetails;
import carrotmoa.carrotmoa.entity.ChatMessage;
import carrotmoa.carrotmoa.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {
    private long userId;
    private long chatRoomId;
    private String message;
    private int state;
    private LocalDateTime createdAt;
    public ChatMessageRequest(ChatMessage chatMessage) {
        this.userId = chatMessage.getUserId();
        this.chatRoomId = chatMessage.getChatRoomId();
        this.message = chatMessage.getMessage();
        this.state = chatMessage.getState();
        this.createdAt = chatMessage.getCreatedAt();
    }
    public ChatMessage toEntityChatMessage() {
        return ChatMessage.builder()
                .userId(this.getUserId())
                .chatRoomId(this.getChatRoomId())
                .message(this.getMessage())
                .state(this.getState())
                .build();
    }
    public ChatMessageRequest(CustomUserDetails user){
        this.userId = user.getUserProfile().getUserId();
        }

    }

