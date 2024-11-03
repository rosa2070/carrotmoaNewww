package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Review;
import carrotmoa.carrotmoa.model.response.AccommodationReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT new carrotmoa.carrotmoa.model.response.AccommodationReviewResponse( " +
            "a.id, r.userId, r.comment, rs.checkOutDate, u.nickname) " +
            "FROM Review r " +
            "JOIN Accommodation a ON a.postId = r.postId " +
            "JOIN Reservation rs ON a.id = rs.accommodationId " +
            "JOIN UserProfile u ON u.userId = r.userId " +
            "WHERE a.id = :accommodationId")
    List<AccommodationReviewResponse> getAccommodationReviews(@Param("accommodationId") Long accommodationId);
}
