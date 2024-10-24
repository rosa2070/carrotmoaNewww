package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.AccommodationAmenity;
import carrotmoa.carrotmoa.model.response.AmenityImageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccommodationAmenityRepository extends JpaRepository<AccommodationAmenity, Long> {
    @Query("SELECT am.id, am.name, am.iconUrl " +
            "FROM Accommodation a " +
            "JOIN AccommodationAmenity aa ON a.id = aa.accommodationId " +
            "JOIN Amenity am ON aa.amenityId = am.id " +
            "WHERE aa.accommodationId = :accommodationId")
    List<Object[]> findAccommodationAmenitiesByAccommodationId(@Param("accommodationId") Long accommodationId);
}
