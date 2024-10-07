package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.AccommodationAmenity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationAmenityRequest {
    private Long accommodationId;
    private Long amenityId;

//    public AccommodationAmenity toAccommodationAmenityEntity() {
//        return AccommodationAmenity.builder()
//                .accommodationId(accommodationId)
//                .amenityId(amenityId)
//                .build();
//    }
}
