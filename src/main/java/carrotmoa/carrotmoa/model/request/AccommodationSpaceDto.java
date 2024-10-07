package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.*;

@Getter
@Setter
public class AccommodationSpaceDto {
    private Long id;
    private Long accommodationId;
    private Long spaceId;
    private Integer count;


//    일단 id없이
    public AccommodationSpace toEntity() {
        return AccommodationSpace.builder()
                .accommodationId(this.accommodationId)
                .spaceId(this.spaceId)
                .count(this.count)
                .build();
    }

}
