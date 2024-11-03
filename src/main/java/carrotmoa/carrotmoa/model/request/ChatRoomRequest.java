package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.ChatRoom;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequest {
    private long roomId;
    private String roomName;
    private int state;


    public ChatRoom toEntityChatRoom() {
        return ChatRoom.builder()
                .roomName(this.roomName)
                .state(1)
                .build();
    }

    public ChatRoomRequest(ChatRoom chatRoom) {
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.state = chatRoom.getState();
    }
}
