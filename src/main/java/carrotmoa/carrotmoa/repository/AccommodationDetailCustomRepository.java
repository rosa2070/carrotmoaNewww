package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationDetailCustomRepository {
    AccommodationDetailResponse getAccommodationDetailById(Long id);
    List<HostManagedAccommodationResponse> findAccommodationsByUserId(Long userId);
}