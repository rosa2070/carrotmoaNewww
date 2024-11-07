package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository  extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllById(long roomId);
}
