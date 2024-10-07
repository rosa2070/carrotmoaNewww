package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.Hit;
import carrotmoa.carrotmoa.entity.Post;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveFleaMarketPostRequest {

    private Long categoryId;
    private Long userId;
    private String title;
    private String content;
    private boolean isDeleted;

    public Post toPostEntity() {
        return Post.builder()
            .categoryId(categoryId)
            .userId(userId)
            .title(title)
            .content(content)
            .build();
    }
}
