package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.model.response.PaymentDetailResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentDetailCustomRepository {

    List<PaymentDetailResponse> getSettlementList(String title, LocalDate startDate, LocalDate endDate);

}
