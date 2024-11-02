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
    // postId를 검색 후 바로 받아왔으면 그걸로 가능한데 accommodationId로 받아왔기 때문에 join 필요
//    @Query("SELECT r.id, r.userId, r.comment, r.updatedAt, p.nickname " +
//            "FROM Review r " +
//            "JOIN UserProfile p ON p.userId = r.userId " +
//            "JOIN Accommodation a ON a.postId = r.postId " +
//            "WHERE a.id = :id")
//    List<Object[]> findReviewByAccommodationId(@Param("id") long id);

    @Query("SELECT new carrotmoa.carrotmoa.model.response.AccommodationReviewResponse( " +
            "a.id, r.userId, r.comment, rs.checkOutDate, u.nickname) " +
            "FROM Review r " +
            "JOIN Reservation rs ON rs.userId = r.userId " +
            "JOIN UserProfile u ON u.userId = r.postId " +
            "JOIN Accommodation a ON a.postId = r.postId " +
            "WHERE a.id = :accommodationId")
    public List<AccommodationReviewResponse> getAccommodationReviews(@Param("accommodationId") Long accommodationId);

}
