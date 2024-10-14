package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("SELECT a.name, a.lotAddress, a.detailAddress, a.price, ai.imageUrl FROM Accommodation a JOIN AccommodationImage ai ON a.id = ai.accommodationId WHERE a.id = :accommodationId")
    List<Object[]> findAccommodationWithImageById(@Param("accommodationId") Long accommodationId);
}
