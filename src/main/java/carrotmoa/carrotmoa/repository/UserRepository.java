package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Amenity;
import carrotmoa.carrotmoa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT a.id, u.userId, u.nickname, u.bio, u.picUrl " +
            "FROM Accommodation a " +
            "JOIN Post p ON a.postId = p.id " +
            "JOIN UserProfile u ON u.userId = p.userId " +
            "WHERE a.id = :id")
    List<Object[]> getUserProfile(@Param("id") Long id);
}
