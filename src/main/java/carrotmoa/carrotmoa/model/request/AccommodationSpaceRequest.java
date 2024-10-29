package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationSpaceRequest implements RequestDTO {
    private long accommodationId;
    private long spaceId;
    private int count;

}
