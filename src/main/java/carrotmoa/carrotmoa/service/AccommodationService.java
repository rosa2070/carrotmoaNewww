package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Transactional
    public Accommodation saveAccommodation(CreateAccommodationRequest request, Long postId) {
        Accommodation accommodation = request.toAccommodationEntity();
        accommodation.setPostId(postId);
        return accommodationRepository.save(accommodation);
    }

    @Transactional
    public void updateAccommodation(Long accommodationId, UpdateAccommodationRequest updateRequest) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        // 총 면적 변경
        if (updateRequest.getTotalArea() != null) {
            log.info("총 면적 변경: {} -> {}", accommodation.getTotalArea(), updateRequest.getTotalArea());
            accommodation.setTotalArea(updateRequest.getTotalArea());
        }

        // 도로명 주소 변경
        if (updateRequest.getRoadAddress() != null) {
            log.info("도로명 주소 변경: {} -> {}", accommodation.getRoadAddress(), updateRequest.getRoadAddress());
            accommodation.setRoadAddress(updateRequest.getRoadAddress());
        }

        // 지번 주소 변경
        if (updateRequest.getLotAddress() != null) {
            log.info("지번 주소 변경: {} -> {}", accommodation.getLotAddress(), updateRequest.getLotAddress());
            accommodation.setLotAddress(updateRequest.getLotAddress());
        }

        // 상세 주소 변경
        if (updateRequest.getDetailAddress() != null) {
            log.info("상세 주소 변경: {} -> {}", accommodation.getDetailAddress(), updateRequest.getDetailAddress());
            accommodation.setDetailAddress(updateRequest.getDetailAddress());
        }

        // 층수 변경
        if (updateRequest.getFloor() != null) {
            log.info("층수 변경: {} -> {}", accommodation.getFloor(), updateRequest.getFloor());
            accommodation.setFloor(updateRequest.getFloor());
        }

        // 총 층수 변경
        if (updateRequest.getTotalFloor() != null) {
            log.info("총 층수 변경: {} -> {}", accommodation.getTotalFloor(), updateRequest.getTotalFloor());
            accommodation.setTotalFloor(updateRequest.getTotalFloor());
        }

        // 가격 변경
        if (updateRequest.getPrice() != null) {
            log.info("가격 변경: {} -> {}", accommodation.getPrice(), updateRequest.getPrice());
            accommodation.setPrice(updateRequest.getPrice());
        }

        // 교통 정보 변경
        if (updateRequest.getTransportationInfo() != null) {
            log.info("교통 정보 변경: {} -> {}", accommodation.getTransportationInfo(), updateRequest.getTransportationInfo());
            accommodation.setTransportationInfo(updateRequest.getTransportationInfo());
        }

        // 변경된 숙소 정보 저장
        accommodationRepository.save(accommodation);
    }
}
