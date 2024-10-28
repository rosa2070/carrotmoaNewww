package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.model.response.ContractDetailResponse;
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
    private final AccommodationRepository accommodationRepository;

    public ReservationService(ReservationRepository reservationRepository, AccommodationRepository accommodationRepository) {
        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Transactional
    public List<BookingListResponse> getBookingList(Long id) {
        List<Object[]> bookings = reservationRepository.findByAccommodationIdAndStatus(id);

        return bookings.stream()
                .map(BookingListResponse::fromData)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContractDetailResponse getContractDeatil(Long id) {
        Object[] info = accommodationRepository.findContractInfo(id);
        return ContractDetailResponse.fromdata(info);
    }
}
