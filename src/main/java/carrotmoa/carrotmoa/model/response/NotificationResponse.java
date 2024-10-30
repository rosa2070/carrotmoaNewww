package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.Notification;
import carrotmoa.carrotmoa.entity.NotificationType;
import carrotmoa.carrotmoa.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private String title; // notification_type 테이블의 title -> join
    private String userName;
    private String picUrl;
    private String message; //
    private String url;
    private LocalDateTime createdAt;

    public NotificationResponse(Notification notification, NotificationType notificationType, UserProfile userProfile) {
        this.title = notificationType.getTitle();
        this.userName = userProfile.getNickname();
        this.picUrl = userProfile.getPicUrl();
        this.message = notification.getMessage();
        this.url = notification.getUrl();
        this.createdAt = notification.getCreatedAt();
    }
}
