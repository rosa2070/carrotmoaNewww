package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.AccommodationLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccommodationLocationRepository extends JpaRepository<AccommodationLocation, Long> {
    Optional<AccommodationLocation> findByAccommodationId(Long accommodationId);
}
