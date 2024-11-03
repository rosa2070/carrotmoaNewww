package carrotmoa.carrotmoa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room", schema = "carrot_moa")
public class ChatRoom extends BaseEntity {
    @Size(max = 255)
    @Column(name = "room_name")
    private String roomName;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "state", nullable = false)
    private int state;

}