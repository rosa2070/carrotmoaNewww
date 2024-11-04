package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.ChatRoomUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    List<ChatRoomUser> findByUserId(long userId);
    @Query("SELECT a.userId FROM ChatRoomUser a WHERE a.userId != :userId AND a.chatRoomId = :chatRoomId")
    Long findRelativeUserId(@Param("userId") long userId, @Param("chatRoomId")long chatRoomId);
}
