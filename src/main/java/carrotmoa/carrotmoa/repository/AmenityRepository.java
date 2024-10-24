package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
