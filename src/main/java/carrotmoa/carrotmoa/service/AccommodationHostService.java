package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.*;
import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import carrotmoa.carrotmoa.repository.*;
import carrotmoa.carrotmoa.util.AwsS3Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AccommodationHostService {

    private final PostRepository postRepository;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationSpaceRepository accommodationSpaceRepository;
    private final AccommodationAmenityRepository accommodationAmenityRepository;
    private final AccommodationImageRepository accommodationImageRepository;
    private final AccommodationDetailCustomRepository accommodationDetailCustomRepository;
    private final AwsS3Utils awsS3Utils;

    public AccommodationHostService(PostRepository postRepository,
                                    AccommodationRepository accommodationRepository,
                                    AccommodationSpaceRepository accommodationSpaceRepository,
                                    AccommodationAmenityRepository accommodationAmenityRepository,
                                    AccommodationImageRepository accommodationImageRepository,
                                    AccommodationDetailCustomRepository accommodationDetailCustomRepository,
                                    AwsS3Utils awsS3Utils) {
        this.postRepository = postRepository;
        this.accommodationRepository = accommodationRepository;
        this.accommodationSpaceRepository = accommodationSpaceRepository;
        this.accommodationAmenityRepository = accommodationAmenityRepository;
        this.accommodationImageRepository = accommodationImageRepository;
        this.accommodationDetailCustomRepository = accommodationDetailCustomRepository;
        this.awsS3Utils = awsS3Utils;
    }

    @Transactional
    public Long createAccommodation(CreateAccommodationRequest createAccommodationRequest) {
        try {
            Post savedPost = savePost(createAccommodationRequest);
            Accommodation savedAccommodation = saveAccommodation(createAccommodationRequest, savedPost);
            saveAccommodationSpaces(createAccommodationRequest.toAccommodationSpaceEntities(), savedAccommodation.getId());
            saveAmenities(savedAccommodation.getId(), createAccommodationRequest.getAmenityIds());
            saveAccommodationImages(savedAccommodation.getId(), createAccommodationRequest.getImages());
            return savedAccommodation.getId();
        } catch (IOException e) {
            log.error("Image upload failed: {}", e.getMessage());
            throw new RuntimeException("Image upload failed", e);
        }
    }

    private Post savePost(CreateAccommodationRequest request) {
        Post post = request.toPostEntity();
        return postRepository.save(post);
    }

    private Accommodation saveAccommodation(CreateAccommodationRequest request, Post post) {
        Accommodation accommodation = request.toAccommodationEntity();
        accommodation.setPostId(post.getId());
        return accommodationRepository.save(accommodation);
    }

    private void saveAccommodationSpaces(List<AccommodationSpace> accommodationSpaces, Long accommodationId) {
        accommodationSpaces.forEach(space -> {
            space.setAccommodationId(accommodationId);
            accommodationSpaceRepository.save(space);
        });
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
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                saveImage(accommodationId, images.get(i), i);
            }
        }
    }

    private void saveImage(Long accommodationId, MultipartFile image, int order) throws IOException {
        String imageUrl = awsS3Utils.uploadRoomImage(accommodationId, image);
        AccommodationImage accommodationImage = AccommodationImage.builder()
                .accommodationId(accommodationId)
                .imageUrl(imageUrl)
                .imageOrder(order)
                .build();
        accommodationImageRepository.save(accommodationImage);
    }

    public AccommodationDetailResponse getAccommodationDetail(Long id) {
        return accommodationDetailCustomRepository.getAccommodationDetailById(id);
    }

    public List<HostManagedAccommodationResponse> getAccommodationsByUserId(Long userId) {
        return accommodationDetailCustomRepository.findAccommodationsByUserId(userId);
    }
}
