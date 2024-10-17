package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.*;
import carrotmoa.carrotmoa.model.request.HostAccommodationRequest;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import carrotmoa.carrotmoa.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccommodationHostService {

    private final PostRepository postRepository;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationSpaceRepository accommodationSpaceRepository;
    private final AccommodationAmenityRepository accommodationAmenityRepository;
    private final AccommodationImageRepository accommodationImageRepository;
    private final AccommodationDetailCustomRepository accommodationDetailCustomRepository;

    private final AwsFileService awsFileService;

    public AccommodationHostService(PostRepository postRepository,
                                    AccommodationRepository accommodationRepository,
                                    AccommodationSpaceRepository accommodationSpaceRepository,
                                    AccommodationAmenityRepository accommodationAmenityRepository,
                                    AccommodationImageRepository accommodationImageRepository,
                                    AccommodationDetailCustomRepository accommodationDetailCustomRepository,
                                    AwsFileService awsFileService) {
        this.postRepository = postRepository;
        this.accommodationRepository = accommodationRepository;
        this.accommodationSpaceRepository = accommodationSpaceRepository;
        this.accommodationAmenityRepository = accommodationAmenityRepository;
        this.accommodationImageRepository = accommodationImageRepository;
        this.accommodationDetailCustomRepository = accommodationDetailCustomRepository;
        this.awsFileService = awsFileService;
    }

    @Transactional
    public Long createAccommodation(HostAccommodationRequest hostAccommodationRequest) {
        try {
            // Post 저장
            Post post = hostAccommodationRequest.toPostEntity();
            Post savedPost = postRepository.save(post);

            // Accommodation 엔티티 생성
            Accommodation accommodation = hostAccommodationRequest.toAccommodationEntity();
            accommodation.setPostId(savedPost.getId());
            Accommodation savedAccommodation = accommodationRepository.save(accommodation);

            // AccommodationSpace 저장
            List<AccommodationSpace> accommodationSpaces = hostAccommodationRequest.toAccommodationSpaceEntities();
            accommodationSpaces.forEach(accommodationSpace -> {
                accommodationSpace.setAccommodationId(savedAccommodation.getId());
                accommodationSpaceRepository.save(accommodationSpace);
            });

            // AccommodationAmenity 저장
            saveAmenities(savedAccommodation.getId(), hostAccommodationRequest.getAmenityIds());

            // AccommodationImage 저장
            saveAccommodationImages(savedAccommodation.getId(), hostAccommodationRequest.getImages());

            return savedAccommodation.getId();
        } catch (IOException e) {
            // IOException 처리
            // 예: 로그 남기기, 사용자에게 에러 메시지 전달하기 등
            log.error("Image upload failed: {}", e.getMessage());
            throw new RuntimeException("Image upload failed");
        }

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

    private void saveAccommodationImages(Long accommodationId, List<MultipartFile> images) throws IOException {
        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);

                // S3에 이미지 업로드 후 URL을 가져오는 로직
                String imageUrl = awsFileService.saveRoomImg(image, accommodationId);

                AccommodationImage accommodationImage = AccommodationImage.builder()
                        .accommodationId(accommodationId)
                        .imageUrl(imageUrl)
                        .imageOrder(i)
                        .build();

                accommodationImageRepository.save(accommodationImage);

            }
        }
    }

    public AccommodationDetailResponse getAccommodationDetail(Long id) {
        return accommodationDetailCustomRepository.getAccommodationDetailById(id);
    }

    // 호스트가 등록한 방 리스트
//    public List<HostManagedAccommodationResponse> getManagedAccommodationsByUserId(Long userId) {
//        List<Object[]> results = accommodationRepository.findAccommodationsByUserId(userId);
//        return results.stream()
//                .map(HostManagedAccommodationResponse::fromData)
//                .collect(Collectors.toList());
//    }


}
