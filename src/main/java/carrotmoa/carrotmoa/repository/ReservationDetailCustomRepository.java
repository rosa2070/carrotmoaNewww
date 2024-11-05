package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.model.response.GuestReservationResponse;
import carrotmoa.carrotmoa.model.response.HostReservationResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDetailCustomRepository {
    List<GuestReservationResponse> getGuestReservations(Long userId);
    List<HostReservationResponse> getHostReservations(Long hostId);
}
