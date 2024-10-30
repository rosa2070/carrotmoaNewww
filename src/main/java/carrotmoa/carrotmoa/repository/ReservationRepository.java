package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Reservation;
import java.util.List;

import carrotmoa.carrotmoa.model.response.BookingDetailResponse;
import carrotmoa.carrotmoa.model.response.BookingListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT new carrotmoa.carrotmoa.model.response.BookingListResponse(" +
            "r.accommodationId, r.checkInDate, r.checkOutDate, r.status, r.totalPrice, " +
            "p.title, a.lotAddress, a.detailAddress, a.floor, u.nickname)" +
//            "i.imageUrl) " +
            "FROM Reservation r " +
            "JOIN Accommodation a ON a.id = r.accommodationId " +
            "JOIN Post p ON p.userId = r.userId " +
            "JOIN UserProfile u ON p.userId = u.userId " +
//            "JOIN AccommodationImage i ON i.accommodationId = r.accommodationId " +
            "WHERE r.userId = :userId")
    List<BookingListResponse> findBookingData(@Param("userId") Long userId);

    @Query("SELECT new carrotmoa.carrotmoa.model.response.BookingDetailResponse( " +
            "a.id, a.lotAddress, a.detailAddress, a.floor, " +
            "p.title, u.nickname) " +
            "FROM Accommodation a " +
            "JOIN Post p ON p.id = a.postId " +
            "JOIN UserProfile u ON u.userId = p.userId " +
            "WHERE a.id = :accommodationId")
    BookingDetailResponse findBookingDetail(@Param("accommodationId") Long accommodationId);
}
