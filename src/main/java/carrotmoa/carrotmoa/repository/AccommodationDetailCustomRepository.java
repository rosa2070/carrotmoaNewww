package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationDetailCustomRepository {
    Accommodation getAccommodationById(Long id);
}
