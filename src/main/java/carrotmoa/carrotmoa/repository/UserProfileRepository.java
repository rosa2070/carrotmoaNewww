package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserId(long userId);
}
