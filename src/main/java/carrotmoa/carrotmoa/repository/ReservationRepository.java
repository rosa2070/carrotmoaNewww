package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(@Param("user_id") Long userId);
}
