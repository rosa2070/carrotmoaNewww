package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.model.response.GuestReservationResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDetailCustomRepository {
    List<GuestReservationResponse> getGuestReservations(Long userId);
}
