package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "community_comment")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityComment extends BaseEntity{
    @Column(name = "community_post_id")
    private Long communityPostId;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "content")
    private String content;
    @Column(name = "is_deleted")
    private boolean isDeleted;

}
