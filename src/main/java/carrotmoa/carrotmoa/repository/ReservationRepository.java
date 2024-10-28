package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Reservation;
import java.util.List;

import carrotmoa.carrotmoa.model.response.BookingListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    List<Reservation> findByUserId(@Param("user_id") Long userId);

//    @Query("SELECT r.id, r.userId, r.accommodationId, r.checkInDate, r.checkOutDate, r.totalPrice, r.status, " +
//            "a.lotAddress, a.detailAddress, ai.imageUrl, p.title " +
//            "FROM Reservation r " +
//            "JOIN Accommodation a ON r.id = a.id " +
//            "JOIN AccommodationImage ai ON ai.accommodationId = r.accommodationId " +
//            "JOIN Post p ON p.userId = r.userId " +
//            "WHERE a.id = :accommodationId")
//    List<Object[]> findByAccommodationId(@Param("accommodationId") Long accommodationId);

    @Query("SELECT r.accommodationId, r.checkInDate, r.checkOutDate, r.status " +
            "FROM Reservation r " +
            "WHERE r.accommodationId = :accommodationId AND r.status IN (1, 3)")
    List<Object[]> findByAccommodationIdAndStatus(@Param("accommodationId") Long accommodationId);
}
