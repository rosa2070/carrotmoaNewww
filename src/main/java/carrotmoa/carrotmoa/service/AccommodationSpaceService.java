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
        if (accommodationSpaces == null || accommodationSpaces.isEmpty()) {
            log.warn("No accommodation spaces to save for accommodation ID: {}", accommodationId);
            return; // 빈 리스트나 null인 경우 조기 반환
        }
    }

    private void saveAccommodationSpace(AccommodationSpace space, Long accommodationId) {
        space.setAccommodationId(accommodationId);
        accommodationSpaceRepository.save(space);
        log.info("Saved accommodation space: {}", space);

    }
}
