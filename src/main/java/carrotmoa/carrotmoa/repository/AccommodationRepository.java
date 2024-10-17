package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

//    @Query("SELECT a.id, a.name, a.lotAddress, a.detailAddress, a.price, " +
//            "MIN(ai.imageUrl) AS imageUrl " +
//            "FROM Accommodation a " +
//            "LEFT JOIN AccommodationImage ai ON a.id = ai.accommodationId " +
//            "WHERE a.userId = :userId " +
//            "GROUP BY a.id, a.name, a.lotAddress, a.detailAddress, a.price " +
//            "ORDER BY a.createdAt DESC")
//        // 최신 등록 순으로 정렬
//    List<Object[]> findAccommodationsByUserId(@Param("userId") Long userId);

//    @Query(value = "SELECT a.id, a.userId, p.title, a.totalArea, " +
//            "a.roadAddress, a.lotAddress, a.detailAddress, a.floor, a.totalFloor, " +
//            "a.price, p.content, a.transportaionInfo, " +
//            "GROUP_CONCAT(DISTINCT ai.imageUrl) AS imageUrls, " +
//            "GROUP_CONCAT(DISTINCT am.amenityId) AS amenities, " +
//            "MAX(CASE WHEN s.spaceId = 1 THEN s.count ELSE 0 END) AS roomCount, " +
//            "MAX(CASE WHEN s.spaceId = 2 THEN s.count ELSE 0 END) AS bathroom, " +
//            "MAX(CASE WHEN s.spaceId = 3 THEN s.count ELSE 0 END) As livingRoomCount, " +
//            "MAX(CASE WHEN s.spaceId = 4 THEN s.count ELSE 0 END) As kitchenCount " +
//            "FROM Accommodation a " +
//            "LEFT JOIN AccommodationImage ai ON a.id = ai.accommodationId " +
//            "LEFT JOIN AccommodationSpace s ON a.id = s.accommodationId " +
//            "LEFT JOIN AccommodationAmenity am ON a.id = am.accommodationId " +
//            "LEFT JOIN Post p ON p.id = a.postId " +
//            "WHERE a.id = :accommodationId " +
//            "GROUP BY a.id, a.userId, a.name,
//> Task :compileJava
//
//BUILD SUCCESSFUL in 5s
//1 actionable task: 1 executed
//오후 5:21:44: Execution finished 'compileJava a.totalArea, a.roadAddress, a.lotAddress, " +
//            "a.detailAddress, a.floor, a.totalFloor, a.price, a.detail, a.transportationInfo", nativeQuery = true)
//    List<Object[]> findAccommodationDetailsById(@Param("accommodationId") Long accommodationId);
}
