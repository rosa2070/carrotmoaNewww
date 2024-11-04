package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.*;
import carrotmoa.carrotmoa.repository.AccommodationImageRepository;
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
    private final AccommodationImageRepository accommodationImageRepository;

    public ReservationService(ReservationRepository reservationRepository, AccommodationImageRepository accommodationImageRepository) {
        this.reservationRepository = reservationRepository;
        this.accommodationImageRepository = accommodationImageRepository;
    }

    @Transactional
    public List<BookingListResponse> getBookingList(Long id) {
        List<Object[]> bookingList = reservationRepository.findBookingData(id);
        return bookingList.stream()
                .map(BookingListResponse::fromData)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingDetailResponse getBookingDetail(Long id) {
        return reservationRepository.findBookingDetail(id);
    }

    @Transactional
    public List<FullCalendarResponse> getBookedDates(Long id) {
        return reservationRepository.findBookedDates(id);
    }

    @Transactional
    public List<AccommodationImageResponse> getAccommodationImageByUserId(Long id) {
        List<Object[]> images = accommodationImageRepository.findByUserId(id);
        return images.stream()
                .map(AccommodationImageResponse::fromData)
                .collect(Collectors.toList());
    }
}
