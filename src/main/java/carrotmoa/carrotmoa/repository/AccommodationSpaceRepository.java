package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface AccommodationSpaceRepository extends JpaRepository<AccommodationSpace, Long> {
    List<AccommodationSpace> findByAccommodationId(Long accommodationId);
}
