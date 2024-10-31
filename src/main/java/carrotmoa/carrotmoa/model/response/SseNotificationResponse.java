package carrotmoa.carrotmoa.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SseNotificationResponse {
    private String title;
    private String senderNickname;
    private String picUrl;
    private String message;
    private String url;
    private boolean isRead;
    private boolean isDeleted;
    private  String elapsedTime;
}
