package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.AccommodationImage;
import carrotmoa.carrotmoa.model.response.AccommodationImageListResponse;
import carrotmoa.carrotmoa.model.response.AccommodationImageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Long> {
    List<AccommodationImageListResponse> findByAccommodationId(@Param("accommodationId") Long accommodationId);

    @Query("SELECT new carrotmoa.carrotmoa.model.response.AccommodationImageResponse(i.accommodationId, i.imageUrl) " +
            "FROM AccommodationImage i " +
            "WHERE i.accommodationId = :id " +
            "ORDER BY i.imageOrder ASC")
    Optional<AccommodationImageResponse> findByAccommodationId(@Param("id") Long id);
}
