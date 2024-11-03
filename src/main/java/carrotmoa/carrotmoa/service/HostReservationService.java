package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.HostReservationResponse;
import carrotmoa.carrotmoa.repository.ReservationDetailCustomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HostReservationService {

    private final ReservationDetailCustomRepository reservationDetailCustomRepository;

    public HostReservationService(ReservationDetailCustomRepository reservationDetailCustomRepository) {
        this.reservationDetailCustomRepository = reservationDetailCustomRepository;
    }

    @Transactional(readOnly = true)
    public List<HostReservationResponse> getHostReservations(Long hostId) {
        return reservationDetailCustomRepository.getHostReservations(hostId);
    }
}
