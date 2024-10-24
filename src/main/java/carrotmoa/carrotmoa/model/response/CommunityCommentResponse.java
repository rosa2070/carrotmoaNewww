package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.CommunityComment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityCommentResponse {
    private Long id;
    private Long communityPostId;
    private Long parentId;
    private Long userId;
    private String content;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommunityCommentResponse(CommunityComment comment) {
        this.id = comment.getId();
        this.communityPostId = comment.getCommunityPostId();
        this.parentId = comment.getParentId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.isDeleted = comment.isDeleted();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    public CommunityComment toCommunityCommentEntity() {
        return CommunityComment.builder()
            .communityPostId(communityPostId)
            .parentId(parentId)
            .userId(userId)
            .content(content)
            .isDeleted(false)
            .build();
    }
}
