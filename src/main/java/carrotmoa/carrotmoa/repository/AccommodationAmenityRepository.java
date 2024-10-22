package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.AccommodationAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationAmenityRepository extends JpaRepository<AccommodationAmenity, Long> {
    List<AccommodationAmenity> findByAccommodationId(Long accommodationId);
    void deleteByAccommodationIdAndAmenityId(Long accommodationId, Long amenityId);
}
