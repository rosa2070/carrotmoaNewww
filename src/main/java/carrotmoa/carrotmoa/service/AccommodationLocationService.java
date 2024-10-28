package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationLocation;
import carrotmoa.carrotmoa.model.request.SaveAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationLocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccommodationLocationService {
    private final AccommodationLocationRepository accommodationLocationRepository;

    public AccommodationLocationService(AccommodationLocationRepository accommodationLocationRepository) {
        this.accommodationLocationRepository = accommodationLocationRepository;
    }

    @Transactional
    public void saveAccommodationLocation(Long accommodationId, SaveAccommodationRequest saveAccommodationRequest) {
        AccommodationLocation accommodationLocation = AccommodationLocation.builder()
                .accommodationId(accommodationId)
                .longitude(saveAccommodationRequest.getLongitude())
                .latitude(saveAccommodationRequest.getLatitude())
                .build();
        accommodationLocationRepository.save(accommodationLocation);
    }

}
