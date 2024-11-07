package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostDetailResponse {

    private Long id;
    private Long postId;
    private Long communityCategoryId;
    private Long userId;
    private String title;
    private String content;
    private boolean isDeleted;
    private String communityCategoryName;
    private String nickname;
    private String picUrl;
    private String region2DepthName;
    private String region3DepthName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String formattedCreatedAt;
    private String formattedUpdatedAt;

    public CommunityPostDetailResponse toCommunityPostDetailResponse(CommunityPost communityPost, Post post, CommunityCategory communityCategory,
                                                                     UserProfile userProfile, UserAddress userAddress) {
        return CommunityPostDetailResponse.builder()
                .id(communityPost.getId())
                .postId(communityPost.getPostId())
                .communityCategoryId(communityPost.getCommunityCategoryId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .isDeleted(post.isDeleted())
                .communityCategoryName(communityCategory.getName())
                .nickname(userProfile.getNickname())
                .picUrl(userProfile.getPicUrl())
                .region2DepthName(userAddress.getRegion2DepthName())
                .region3DepthName(userAddress.getRegion3DepthName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public CommunityPostDetailResponse(Long id, Long postId, Long communityCategoryId, Long userId, String title, String content, boolean isDeleted, String communityCategoryName, String nickname, String picUrl, String region2DepthName, String region3DepthName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.communityCategoryId = communityCategoryId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.communityCategoryName = communityCategoryName;
        this.nickname = nickname;
        this.picUrl = picUrl;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}