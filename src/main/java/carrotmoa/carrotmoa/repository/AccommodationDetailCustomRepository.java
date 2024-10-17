package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationDetailCustomRepository {
//    Accommodation getAccommodationById(Long id);
    AccommodationDetailResponse getAccommodationDetailById(Long id);
}
