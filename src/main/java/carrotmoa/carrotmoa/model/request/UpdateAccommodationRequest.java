package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.AccommodationSpace;
import carrotmoa.carrotmoa.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Slf4j
public class UpdateAccommodationRequest {
    private Long id; // 숙소 ID
    private CreateAccommodationRequest createAccommodationRequest; // composition(합성)

    public Accommodation toAccommodationEntity() {
        return createAccommodationRequest.toAccommodationEntity();
    }

    public Post toPostEntity() {
        return createAccommodationRequest.toPostEntity();
    }

    public List<AccommodationSpace> toAccommodationSpaceEntities() {
        return createAccommodationRequest.toAccommodationSpaceEntities();
    }

    public void logRequestDetails() {
        log.info("Updating Accommodation ID: {}", id);
        createAccommodationRequest.logRequestDetails();
    }








}
