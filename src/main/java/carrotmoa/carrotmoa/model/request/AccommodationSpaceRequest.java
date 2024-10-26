package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationSpaceRequest implements RequestDTO {
    private long accommodationId;
    private long spaceId;
    private int count;

}
