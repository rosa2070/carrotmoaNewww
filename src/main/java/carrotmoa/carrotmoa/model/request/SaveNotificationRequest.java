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

    public SaveNotificationRequest(Notification notification, Long receiverId, Long senderId, String receiverUserName, String messageContent) {
        this.typeId = notification.getTypeId();
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = formatMessage(receiverUserName, messageContent);
        this.url = notification.getUrl();
        this.isRead = false;
        this.isDeleted = false;
    }

    private String formatMessage(String receiverUserName, String messageContent) {
        return receiverUserName + "님: \"" + messageContent + "\"";
    }

}
