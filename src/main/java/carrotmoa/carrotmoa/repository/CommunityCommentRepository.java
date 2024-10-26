package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.CommunityComment;
import java.util.List;

import carrotmoa.carrotmoa.model.response.CommunityCommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    @Query("select new carrotmoa.carrotmoa.model.response.CommunityCommentResponse (c, up, ua) " +
            "from CommunityComment c join UserProfile up on c.userId = up.userId join UserAddress ua on c.userId = ua.userId where c.communityPostId = :communityPostId and c.isDeleted = false order by c.createdAt asc")
    List<CommunityCommentResponse> findActiveCommentsByCommunityPostId(@Param("communityPostId") Long communityPostId);

    @Query("select count(c) from CommunityComment c where c.communityPostId = :communityPostId and c.isDeleted = false")
    int countByCommunityPostId(@Param("communityPostId") Long communityPostId);






}
