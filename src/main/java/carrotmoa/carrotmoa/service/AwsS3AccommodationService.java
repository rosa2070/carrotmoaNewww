package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.util.AwsS3Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class AwsS3AccommodationService {

    private final AwsS3Utils awsS3Utils;

    public AwsS3AccommodationService(AwsS3Utils awsS3Utils) {
        this.awsS3Utils = awsS3Utils;
    }

    // 특정 roomId 폴더에 이미지를 S3에 업로드하는 메서드
    public String uploadRoomImage(Long roomId, MultipartFile file) throws IOException {
        String fileName = "room/" + roomId + "/" + UUID.randomUUID() + awsS3Utils.getFileExtension(file.getOriginalFilename());

        // S3에 이미지 업로드
        awsS3Utils.uploadImage(fileName, file);

        // 업로드된 이미지 URL 반환
        String imageUrl = awsS3Utils.getImageUrl(fileName);

        // 성공적으로 저장된 경우 로그 출력
        log.info("Uploaded image to S3: {}", imageUrl);

        return imageUrl; // 업로드된 이미지 URL 반환
    }
}