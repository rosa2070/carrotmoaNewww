package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.ChatRoom;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequest {
    private long roomId;
    private String roomName;
    private int state;
    private LocalDateTime createdAt;


    public ChatRoom toEntityChatRoom() {
        return ChatRoom.builder()
                .roomName(this.roomName)
                .state(0)
                .build();
    }

    public ChatRoomRequest(ChatRoom chatRoom,String findRelativeUserNickname) {
        this.roomId = chatRoom.getId();
        this.state = chatRoom.getState();
        if(chatRoom.getRoomName() != null) {
            this.roomName = chatRoom.getRoomName();
        } else {
            this.roomName = findRelativeUserNickname;
        }
        this.createdAt = chatRoom.getCreatedAt();
    }
}
