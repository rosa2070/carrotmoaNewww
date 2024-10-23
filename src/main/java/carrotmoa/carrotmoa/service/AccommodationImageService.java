package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationImage;
import carrotmoa.carrotmoa.repository.AccommodationImageRepository;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.util.AwsS3Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        // 기존 이미지 삭제
        deleteExistingImages(existingImageUrls);

        // 새 이미지 저장
        saveAccommodationImages(accommodationId, newImages);
    }

    @Transactional // 이 메서드도 트랜잭션으로 처리할 수 있습니다
    public void saveAccommodationImages(Long accommodationId, List<MultipartFile> images) throws IOException {
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                String imageUrl = uploadAndSaveImage(accommodationId, images.get(i), i);
            }
        }
    }

    // 기존 이미지 삭제
    private void deleteExistingImages(List<String> existingImageUrls) throws IOException {
        if (existingImageUrls != null && !existingImageUrls.isEmpty()) {
            for (String imageUrl : existingImageUrls) {
                awsS3Utils.deleteImageFromUrl(imageUrl); // S3에서 이미지 삭제
                log.info("Deleted image from S3: {}", imageUrl); // 삭제 로그 추가
                // 데이터베이스에서 이미지 메타데이터 삭제 필요시 추가 로직
            }
        }
    }

    // s3 직접 업로드 + url 데베에 저장
    private String uploadAndSaveImage(Long accommodationId, MultipartFile image, int order) throws IOException {
        String imageUrl = uploadRoomImage(accommodationId, image);
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

    // 특정 roomId 폴더에 이미지를 S3에 업로드하는 메서드
    private String uploadRoomImage(Long roomId, MultipartFile file) {
        String fileName = "room/" + roomId + "/" + UUID.randomUUID() + awsS3Utils.getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

        // S3에 이미지 업로드
        awsS3Utils.uploadImage(fileName, file);

        // 업로드된 이미지 URL 반환
        String imageUrl = awsS3Utils.getImageUrl(fileName);

        // 성공적으로 저장된 경우 로그 출력
        log.info("Uploaded image to S3: {}", imageUrl);

        return imageUrl; // 업로드된 이미지 URL 반환
    }

    private String extractFilePathFromUrl(String url) {
        // URL을 슬래시('/')로 분리
        String[] parts = url.split("/");

        // room/42/와 파일 이름을 결합
        return parts[parts.length - 2] + "/" + parts[parts.length - 1];
    }

}
