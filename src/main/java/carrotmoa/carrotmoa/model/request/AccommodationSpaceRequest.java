package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.*;

@Getter
@Setter
public class AccommodationSpaceRequest {
    private Long accommodationId;
    private Long spaceId;
    private Integer count;

//    public AccommodationSpace toAccommodationSpaceEntity() {
//        return AccommodationSpace.builder()
//                .accommodationId(accommodationId)
//                .spaceId(spaceId)
//                .count(count)
//                .build();
//    }

}
