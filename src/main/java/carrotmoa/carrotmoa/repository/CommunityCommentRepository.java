package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.CommunityComment;
import java.util.List;
import java.util.Optional;

import carrotmoa.carrotmoa.model.response.CommunityCommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    @Query("SELECT new carrotmoa.carrotmoa.model.response.CommunityCommentResponse(c, up, ua) " +
            "FROM CommunityComment c " +
            "JOIN UserProfile up ON c.userId = up.userId " +
            "JOIN UserAddress ua ON c.userId = ua.userId " +
            "WHERE c.communityPostId = :communityPostId AND c.isDeleted = false " +
            "ORDER BY CASE WHEN c.parentId IS NULL THEN 0 ELSE 1 END, c.createdAt ASC")
    List<CommunityCommentResponse> findActiveCommentsByCommunityPostId(@Param("communityPostId") Long communityPostId);

    @Query("select count(c) from CommunityComment c where c.communityPostId = :communityPostId and c.isDeleted = false")
    int countByCommunityPostId(@Param("communityPostId") Long communityPostId);

    Optional<CommunityComment> findByIdAndCommunityPostId(Long commentId, Long communityPostId);
}
