package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<Payment> findByImpUid(String impUid);
}
