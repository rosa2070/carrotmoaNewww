package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.*;

@Getter
@Setter
public class AccommodationSpaceRequest {
    private Long accommodationId;
    private Long spaceId;
    private Integer count;

}
