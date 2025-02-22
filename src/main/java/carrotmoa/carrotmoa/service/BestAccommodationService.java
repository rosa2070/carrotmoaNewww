package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.BestAccommodationResponse;
import carrotmoa.carrotmoa.repository.BestAccommodationCustomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BestAccommodationService {

    private final BestAccommodationCustomRepository bestAccommodationCustomRepository;

    public BestAccommodationService(BestAccommodationCustomRepository bestAccommodationCustomRepository) {
        this.bestAccommodationCustomRepository = bestAccommodationCustomRepository;
    }

    public List<BestAccommodationResponse> getBestAccommodations() {
        return bestAccommodationCustomRepository.getBestAccommodations();
    }
}
