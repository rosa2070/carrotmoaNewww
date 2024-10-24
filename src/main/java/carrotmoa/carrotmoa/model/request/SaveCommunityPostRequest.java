package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.CommunityPost;
import carrotmoa.carrotmoa.entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveCommunityPostRequest {
    // post 테이블
    private Long id;
    private Long serviceId;
    private Long userId;
    private String title;
    private String content;
    private boolean isDeleted;
    // community_post 테이블
    private Long communityPostId; // 기본키 id인데 중복...
    private Long postId;
    private Long communityCategoryId;

    public SaveCommunityPostRequest(Post post) {
        this.id = post.getId();
        this.serviceId = post.getServiceId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.content =  post.getContent();
        this.isDeleted = post.isDeleted();
    }

    public Post toPostEntity() {
        return Post.builder()
                .serviceId(serviceId)
                .userId(userId)
                .title(title)
                .content(content)
                .isDeleted(false)
                .build();
    }

    public SaveCommunityPostRequest(CommunityPost communityPost) {
        this.communityPostId = communityPost.getId();
        this.postId = communityPost.getPostId();
        this.communityCategoryId = communityPost.getCommunityCategoryId();
    }

    public CommunityPost toCommunityPostEntity(Long id) {
        return CommunityPost.builder()
                .postId(id)
                .communityCategoryId(communityCategoryId)
                .build();
    }



}
