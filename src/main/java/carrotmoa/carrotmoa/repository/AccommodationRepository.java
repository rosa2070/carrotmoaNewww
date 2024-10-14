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

    @Query("SELECT a.id, a.name, a.lotAddress, a.detailAddress, a.price, " +
            "MIN(ai.imageUrl) AS imageUrl " +
            "FROM Accommodation a " +
            "LEFT JOIN AccommodationImage ai ON a.id = ai.accommodationId " +
            "WHERE a.userId = :userId " +
            "GROUP BY a.id, a.name, a.lotAddress, a.detailAddress, a.price")
    List<Object[]> findAccommodationsByUserId(@Param("userId") Long userId);




}
