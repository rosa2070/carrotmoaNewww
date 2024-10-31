package carrotmoa.carrotmoa;

import carrotmoa.carrotmoa.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "chat_message", schema = "carrot_moa")
public class ChatMessage extends BaseEntity {
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "chat_room_id", nullable = false)
    private Long chatRoomId;

    @Size(max = 255)
    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "state", nullable = false)
    private Integer state;



}