package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationAmenity;
import carrotmoa.carrotmoa.repository.AccommodationAmenityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccommodationAmenityService {
    private final AccommodationAmenityRepository accommodationAmenityRepository;

    public AccommodationAmenityService(AccommodationAmenityRepository accommodationAmenityRepository) {
        this.accommodationAmenityRepository = accommodationAmenityRepository;
    }

    public void saveAmenities(Long accommodationId, List<Long> amenityIds) {
        if (amenityIds != null) {
            amenityIds.forEach(amenityId -> {
                AccommodationAmenity accommodationAmenity = new AccommodationAmenity();
                accommodationAmenity.setAccommodationId(accommodationId);
                accommodationAmenity.setAmenityId(amenityId);
                accommodationAmenityRepository.save(accommodationAmenity);
            });
        }
    }
}
