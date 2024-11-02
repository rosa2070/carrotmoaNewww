package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.PaymentDetailResponse;
import carrotmoa.carrotmoa.repository.PaymentDetailCustomRepository;
import carrotmoa.carrotmoa.repository.PaymentDetailRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SettlementService {

    private final PaymentDetailCustomRepository paymentDetailCustomRepository;

    public SettlementService(PaymentDetailCustomRepository paymentDetailCustomRepository) {
        this.paymentDetailCustomRepository = paymentDetailCustomRepository;
    }

    public List<PaymentDetailResponse> getSettlementList(String title, LocalDate startDate, LocalDate endDate) {
        return paymentDetailCustomRepository.getSettlementList(title, startDate, endDate);
    }




}
