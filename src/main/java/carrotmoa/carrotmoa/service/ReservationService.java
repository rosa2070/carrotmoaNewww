package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.AccommodationImageListResponse;
import carrotmoa.carrotmoa.model.response.BookingDetailResponse;
import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.model.response.ContractDetailResponse;
import carrotmoa.carrotmoa.repository.AccommodationImageRepository;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    // 게스트 예약 확인하는 페이지에서 사용
    // guest/booking/list/{id}
    public List<BookingListResponse> getBookingList(Long id) {
        return reservationRepository.findBookingData(id);
    }

    @Transactional
    public BookingDetailResponse getBookingDetail(Long id) {
        return reservationRepository.findBookingDetail(id);
    }
}
