package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Reservation;
import java.util.List;

import carrotmoa.carrotmoa.model.response.BookingDetailResponse;
import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.model.response.FullCalendarResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    @Query("SELECT new carrotmoa.carrotmoa.model.response.BookingListResponse( " +
//            "r.accommodationId, r.checkInDate, r.checkOutDate, r.status, r.totalPrice, " +
//            "p.title, a.lotAddress, a.detailAddress, a.floor, u.nickname) " +
//            "FROM Reservation r " +
//            "JOIN Accommodation a ON a.id = r.accommodationId " +
//            "JOIN Post p ON p.userId = r.userId " +
//            "JOIN UserProfile u ON p.userId = u.userId " +
//            "WHERE r.userId = :userId")
//    List<BookingListResponse> findBookingData(@Param("userId") Long userId);

    @Query("SELECT r.accommodationId, r.checkInDate, r.checkOutDate, r.status, r.totalPrice, " +
            "p.title, a.lotAddress, a.detailAddress, a.floor, u.nickname, MIN(i.imageUrl) " +
            "FROM Reservation r " +
            "JOIN Accommodation a ON a.id = r.accommodationId " +
            "JOIN AccommodationImage i ON a.id = i.accommodationId " +
            "JOIN Post p ON p.userId = r.userId " +
            "JOIN UserProfile u ON p.userId = u.userId " +
            "WHERE r.userId = :userId " +
            "GROUP BY r.accommodationId, r.checkInDate, r.checkOutDate, r.status, r.totalPrice, " +
            "p.title, a.lotAddress, a.detailAddress, a.floor, u.nickname")
    List<Object[]> findBookingData(@Param("userId") Long userId);

    @Query("SELECT new carrotmoa.carrotmoa.model.response.BookingDetailResponse( " +
            "a.id, a.lotAddress, a.detailAddress, a.floor, " +
            "p.title, u.nickname) " +
            "FROM Accommodation a " +
            "JOIN Post p ON p.id = a.postId " +
            "JOIN UserProfile u ON u.userId = p.userId " +
            "WHERE a.id = :accommodationId")
    BookingDetailResponse findBookingDetail(@Param("accommodationId") Long accommodationId);

    // full calendar
    @Query("SELECT new carrotmoa.carrotmoa.model.response.FullCalendarResponse( " +
            "r.checkInDate, r.checkOutDate) " +
            "FROM Reservation r " +
            "WHERE r.accommodationId = :accommodationId")
    List<FullCalendarResponse> findBookedDates(@Param("accommodationId") Long accommodationId);
}
