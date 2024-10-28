package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, AccommodationRepository accommodationRepository) {
        this.reservationRepository = reservationRepository;
    }

//    @Transactional
//    public List<ReservationResponse> getUserReservations(Long id) {
//        List<Reservation> reservations = reservationRepository.findByUserId(id);
//
//        return reservations.stream()
//                .map(ReservationResponse::fromData)
//                .collect(Collectors.toList());
//    }
    @Transactional
    public List<BookingListResponse> getBookingList(Long id) {
        List<Object[]> bookings = reservationRepository.findByAccommodationIdAndStatus(id);

        return bookings.stream()
                .map(BookingListResponse::fromData)
                .collect(Collectors.toList());
    }
}
