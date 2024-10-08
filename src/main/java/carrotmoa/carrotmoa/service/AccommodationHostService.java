package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.AccommodationAmenity;
import carrotmoa.carrotmoa.entity.AccommodationSpace;
import carrotmoa.carrotmoa.model.request.AccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationAmenityRepository;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.repository.AccommodationSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccommodationHostService {

    // 생성자 주입으로!!

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    AccommodationSpaceRepository accommodationSpaceRepository;

    @Autowired
    AccommodationAmenityRepository accommodationAmenityRepository;

//    @Transactional
//    public Long saveTest(AccommodationRequest accommodationRequest) {
//
//        Accommodation accommodation = accommodationRepository.save(accommodationRequest.toAccommodationEntity());
//
//        return accommodation.getId();
//    }

    @Transactional
    public Long createAccommodation(AccommodationRequest accommodationRequest) {
        // Accommodation 엔티티 생성
        Accommodation accommodation = accommodationRequest.toAccommodationEntity();
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        // AccommodationSpace 저장
        List<AccommodationSpace> accommodationSpaces = accommodationRequest.toAccommodationSpaceEntities();
        accommodationSpaces.forEach(accommodationSpace -> {
            accommodationSpace.setAccommodationId(savedAccommodation.getId());
            accommodationSpaceRepository.save(accommodationSpace);
        });

        // AccommodationAmenity 저장
        saveAmenities(savedAccommodation.getId(),accommodationRequest.getAmenityIds());

        return savedAccommodation.getId();

    }

    private void saveAmenities(Long accommodationId, List<Long> amenityIds) {
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
