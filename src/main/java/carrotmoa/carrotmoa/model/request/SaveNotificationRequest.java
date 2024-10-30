package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.Notification;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SaveNotificationRequest {
    private Long typeId;
    private Long receiverId;
    private Long senderId;
    private String message;
    private String url;
    private boolean isRead;
    private boolean isDeleted;

    public SaveNotificationRequest(Notification notification, Long receiverId, Long senderId) {
        this.typeId = notification.getTypeId();
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = notification.getMessage();
        this.url = notification.getUrl();
        this.isRead = false;
        this.isDeleted = false;
    }


}
