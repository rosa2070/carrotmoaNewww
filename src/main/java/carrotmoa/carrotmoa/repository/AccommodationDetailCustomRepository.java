package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationDetailCustomRepository {
    AccommodationDetailResponse getAccommodationDetailById(Long id);
    List<HostManagedAccommodationResponse> findAccommodationsByUserId(Long userId, Long lastId, int limit);
    List<HostManagedAccommodationResponse> getAllHostRooms(Long userId);
}