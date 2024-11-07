package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomId(long chatRoomId);
}
