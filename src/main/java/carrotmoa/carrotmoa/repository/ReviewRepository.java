package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
