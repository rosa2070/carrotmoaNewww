package carrotmoa.carrotmoa;

import carrotmoa.carrotmoa.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "chat_room_user", schema = "carrot_moa")
public class ChatRoomUser extends BaseEntity {
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "chat_room_id", nullable = false)
    private Long chatRoomId;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "state", nullable = false)
    private Integer state;

}