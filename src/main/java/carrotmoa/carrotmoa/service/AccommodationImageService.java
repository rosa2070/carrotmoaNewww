package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationImage;
import carrotmoa.carrotmoa.repository.AccommodationImageRepository;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.util.AwsS3Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AccommodationImageService {
    private final AccommodationImageRepository accommodationImageRepository;
    private final AwsS3Utils awsS3Utils;

    public AccommodationImageService(AccommodationImageRepository accommodationImageRepository, AwsS3Utils awsS3Utils) {
        this.accommodationImageRepository = accommodationImageRepository;
        this.awsS3Utils = awsS3Utils;
    }

    public void saveAccommodationImages(Long accommodationId, List<MultipartFile> images) throws IOException {
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

}
