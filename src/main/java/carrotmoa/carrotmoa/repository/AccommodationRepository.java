package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import java.util.List;

import carrotmoa.carrotmoa.model.response.AccommodationAvailableResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a.id, p.title, a.roadAddress, a.price, MIN(i.imageUrl) AS imageUrl, " +
        "MAX(CASE WHEN s.spaceId = 1 THEN s.count ELSE 0 END) AS roomCount, " +  // 방 개수
        "MAX(CASE WHEN s.spaceId = 2 THEN s.count ELSE 0 END) AS bathroomCount, " +  // 화장실 개수
        "MAX(CASE WHEN s.spaceId = 3 THEN s.count ELSE 0 END) AS livingRoomCount, " + // 거실 개수
        "MAX(CASE WHEN s.spaceId = 4 THEN s.count ELSE 0 END) AS kitchenCount " +  // 주방 개수
        "FROM Accommodation a " +
        "JOIN Post p ON p.id = a.postId " +
        "LEFT JOIN AccommodationImage i ON a.id = i.accommodationId " +
        "LEFT JOIN AccommodationSpace s ON a.id = s.accommodationId " +
        "WHERE p.isDeleted = false AND a.lotAddress LIKE %:keyword% " +
        "GROUP BY a.id, p.title, a.roadAddress, a.price")
    List<Object[]> searchAccommodationByKeyword(@Param("keyword") String keyword);

//    Long findPostIdById(@Param("id") Long id);
    @Query("SELECT p.id FROM Post p " +
            "JOIN Accommodation a ON a.postId = p.id " +
            "WHERE p.id = :id")
    Long findPostIdById(@Param("id") Long id);

    @Query("SELECT a.id, p.title, l.latitude, l.longitude, a.lotAddress, MIN(i.imageUrl) AS imageUrl " +
            "FROM Accommodation a " +
            "JOIN AccommodationImage i ON i.accommodationId = a.id " +
            "JOIN Post p ON p.id = a.postId " +
            "JOIN AccommodationLocation l ON a.id = l.accommodationId " +
            "GROUP BY a.id, p.title, l.latitude, l.longitude, a.lotAddress ")
    List<Object[]> findAllAvailableAccommodations();
}