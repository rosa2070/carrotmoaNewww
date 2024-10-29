package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Reservation;
import java.util.List;

import carrotmoa.carrotmoa.model.response.BookingListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT new carrotmoa.carrotmoa.model.response.BookingListResponse(" +
            "r.accommodationId, r.checkInDate, r.checkOutDate, r.status) " +
            "FROM Reservation r " +
            "WHERE r.userId = :userId")
    List<BookingListResponse> findBookingData(@Param("userId") Long userId);
}
