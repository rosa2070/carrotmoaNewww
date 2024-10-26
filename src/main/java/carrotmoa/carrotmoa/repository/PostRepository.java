package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.response.CommunityPostListResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT new carrotmoa.carrotmoa.model.response.CommunityPostListResponse(p, c, ca, ua, i, 0) " +
        "FROM Post p " +
        "JOIN CommunityPost c ON p.id = c.postId " +
        "LEFT JOIN PostImage i ON p.id = i.postId " +
        "JOIN CommunityCategory ca ON c.communityCategoryId = ca.id " +
        "JOIN UserAddress ua ON p.userId = ua.userId " +
        "WHERE p.serviceId = :serviceId AND p.isDeleted = false " +
        "order by p.createdAt desc ")
    List<CommunityPostListResponse> getAllCommunityPosts(@Param("serviceId") Long serviceId);

    @Modifying(clearAutomatically = true)
    @Query("update Post p " +
        "set p.isDeleted = true, p.updatedAt = current_timestamp " +
        "where p.id = (select cp.postId from CommunityPost cp where cp.id = :communityPostId) ")
    int deleteByCommunityPostId(@Param("communityPostId") Long communityPostId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.isDeleted = true WHERE p.id = :postId")
    void markAsDeleted(@Param("postId") Long postId);


}
