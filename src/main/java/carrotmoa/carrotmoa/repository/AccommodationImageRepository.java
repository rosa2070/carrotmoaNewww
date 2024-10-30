package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.AccommodationImage;
import carrotmoa.carrotmoa.model.response.AccommodationImageListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Long> {
    List<AccommodationImageListResponse> findByAccommodationId(@Param("accommodationId") Long accommodationId);

    void deleteByImageUrl(String imageUrl);
}
