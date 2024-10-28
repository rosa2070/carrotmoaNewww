package carrotmoa.carrotmoa.entity;

import carrotmoa.carrotmoa.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type_id")
    private NotificationType typeId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "message")
    private String message;

    @Column(name = "is_deleted")
    private boolean isDeleted;


}
