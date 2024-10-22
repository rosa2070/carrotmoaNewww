package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import carrotmoa.carrotmoa.repository.AccommodationSpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccommodationSpaceService {
    private final AccommodationSpaceRepository accommodationSpaceRepository;

    public AccommodationSpaceService(AccommodationSpaceRepository accommodationSpaceRepository) {
        this.accommodationSpaceRepository = accommodationSpaceRepository;
    }

    public void saveAccommodationSpaces(List<AccommodationSpace> accommodationSpaces, Long accommodationId) {
        accommodationSpaces.forEach(accommodationSpace -> saveAccommodationSpace(accommodationSpace, accommodationId));
    }

    private void saveAccommodationSpace(AccommodationSpace accommodationSpace, Long accommodationId) {
        accommodationSpace.setAccommodationId(accommodationId);
        accommodationSpaceRepository.save(accommodationSpace);
        log.info("Saved accommodation space: {}", accommodationSpace);

    }
}
