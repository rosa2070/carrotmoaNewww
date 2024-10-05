package carrotmoa.carrotmoa.dto;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccommodationSpaceDto {
    private Long id;
    private Long accommodationId;
    private Long spaceId;
    private Integer count;

    public AccommodationSpaceDto(AccommodationSpace accommodationSpace) {
        this.id = accommodationSpace.getId();
        this.accommodationId = accommodationSpace.getAccommodationId();
        this.spaceId = accommodationSpace.getSpaceId();
        this.count = accommodationSpace.getCount();
    }

//    일단 id없이
    public AccommodationSpace toEntity() {
        return AccommodationSpace.builder()
                .accommodationId(this.accommodationId)
                .spaceId(this.spaceId)
                .count(this.count)
                .build();
    }

}
