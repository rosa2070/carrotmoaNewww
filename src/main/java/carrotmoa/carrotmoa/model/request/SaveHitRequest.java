package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.Hit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveHitRequest {

    private Long postId;
    private Long userId;

    public Hit toHitEntity() {
        return Hit.builder()
            .postId(postId)
            .userId(userId)
            .build();
    }
}
