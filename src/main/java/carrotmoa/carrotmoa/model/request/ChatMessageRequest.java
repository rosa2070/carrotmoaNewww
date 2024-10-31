package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.ChatMessage;
import carrotmoa.carrotmoa.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {
    private long userId;
    private long chatRoomId;
    private String message;
    private int state;
    private String nickname;
    private String picUrl;

    @Value("${spring.user.profile.default-image}")
    private String defaultProfileImageUrl;
    public ChatMessageRequest(ChatMessage chatMessage) {
        this.userId = chatMessage.getUserId();
        this.chatRoomId = chatMessage.getChatRoomId();
        this.message = chatMessage.getMessage();
        this.state = chatMessage.getState();
        this.nickname = chatMessage.getNickname();
        if(chatMessage.getPicUrl() != null) {
        this.picUrl = chatMessage.getPicUrl();
        } else  {
            this.picUrl = defaultProfileImageUrl;
        }
    }
    public ChatMessage toEntityChatMessage() {
        return ChatMessage.builder()
                .userId(this.getUserId())
                .chatRoomId(this.getChatRoomId())
                .message(this.getMessage())
                .state(this.getState())
                .nickname(this.getNickname())
                .picUrl(this.getPicUrl())
                .build();
    }
}
