package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.ChatMessage;
import carrotmoa.carrotmoa.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private long userId;
    private long chatRoomId;
    private String message;
    private int state;
    private String nickname;
    private String picUrl;

    @Value("${spring.user.profile.default-image}")
    private String defaultProfileImageUrl;
    public ChatMessageResponse(ChatMessage chatMessage, UserProfile userProfile) {
        this.userId = chatMessage.getUserId();
        this.chatRoomId = chatMessage.getChatRoomId();
        this.message = chatMessage.getMessage();
        this.state = chatMessage.getState();
        this.nickname = userProfile.getNickname();
        if(userProfile.getPicUrl() != null) {
        this.picUrl = userProfile.getPicUrl();
        } else  {
            this.picUrl = defaultProfileImageUrl;
        }
    }
}
