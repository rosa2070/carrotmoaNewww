package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationImage;
import carrotmoa.carrotmoa.repository.AccommodationImageRepository;
import java.io.IOException;
import java.util.List;

import carrotmoa.carrotmoa.util.AwsS3Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AccommodationImageService {
    private final AccommodationImageRepository accommodationImageRepository;
    private final AwsS3Utils awsS3Utils;

    public AccommodationImageService(AccommodationImageRepository accommodationImageRepository, AwsS3Utils awsS3Utils) {
        this.accommodationImageRepository = accommodationImageRepository;
        this.awsS3Utils = awsS3Utils;
    }

    @Transactional
    public void updateAccommodationImages(Long accommodationId, List<MultipartFile> newImages, List<String> existingImageUrls) throws IOException {
        // 새 이미지가 있을 경우에만 기존 이미지 삭제
        if (newImages != null && !newImages.isEmpty()) {
            deleteExistingImages(existingImageUrls);
        }

        // 새 이미지 저장
        saveAccommodationImages(accommodationId, newImages);
    }

    @Transactional
    public void saveAccommodationImages(Long accommodationId, List<MultipartFile> images) throws IOException {
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                String imageUrl = uploadAndSaveImage(accommodationId, images.get(i), i);
            }
        }
    }

    // 기존 이미지 삭제 ( 메타 데이터 삭제 + s3 이미지 삭제)
    private void deleteExistingImages(List<String> existingImageUrls) throws IOException {
        if (existingImageUrls != null && !existingImageUrls.isEmpty()) {
            for (String imageUrl : existingImageUrls) {
                // 데이터베이스에서 이미지 메타데이터 삭제
                deleteImageMetadata(imageUrl);

                // S3에서 이미지 삭제
                awsS3Utils.deleteImageFromUrl(imageUrl);
                log.info("Deleted image from S3: {}", imageUrl); // 삭제 로그 추가
            }
        }
    }

    // 데이터베이스에서 이미지 메타데이터 삭제 메서드
    private void deleteImageMetadata(String imageUrl) {
        // URL에서 accommodationId 추출 (URL 형식에 따라 조정 필요)
        accommodationImageRepository.deleteByImageUrl(imageUrl);
        log.info("Deleted image from metadata: {}", imageUrl);
    }


    // s3 직접 업로드 + url 데베에 저장
    private String uploadAndSaveImage(Long accommodationId, MultipartFile image, int order) throws IOException {
        String imageUrl = awsS3Utils.uploadImageToFolder("room", accommodationId, image);
        saveImageMetadata(accommodationId, imageUrl, order);
        return imageUrl;
    }

    // url만 데베에 저장
    private void saveImageMetadata(Long accommodationId, String imageUrl, int order) {
        AccommodationImage accommodationImage = AccommodationImage.builder()
            .accommodationId(accommodationId)
            .imageUrl(imageUrl)
            .imageOrder(order)
            .build();
        accommodationImageRepository.save(accommodationImage);
    }

}
