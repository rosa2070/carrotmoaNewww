package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public Accommodation saveAccommodation(CreateAccommodationRequest request, Long postId) {
        Accommodation accommodation = request.toAccommodationEntity();
        accommodation.setPostId(postId);
        return accommodationRepository.save(accommodation);
    }
}
