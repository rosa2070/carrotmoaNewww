package carrotmoa.carrotmoa.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Utils {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // 특정 roomId에 이미지를 S3에 업로드하는 메서드
    public String uploadRoomImage(Long roomId, MultipartFile file) {
        String fileName = "room/" + roomId + "/" + UUID.randomUUID() + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        uploadImage(fileName, file); // S3에 이미지 업로드
        return getImageUrl(fileName); // 업로드된 이미지 URL 반환
    }

    // getBytes와 fromInputStream()의 차이 공부
    public void uploadImage(String fileName, MultipartFile file) {
        try {
            // S3에 업로드할 객체 요청을 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName) // 업로드할 S3 버킷의 이름
                    .key(fileName) // S3에서의 파일 이름 (경로 포함)
                    .contentType(file.getContentType()) // 파일의 콘텐츠 타입 (예: image/jpeg)
                    .build();

            // s3에 파일 업로드
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()) // InputStream으로부터 RequestBody를 생성, 실제 파일 데이터 포함
            );

            log.info("Uploaded image: {}", fileName);

        } catch (IOException e) {
            log.error("Error uploading image: {}", fileName, e);
            throw new RuntimeException("Error uploading file: " + fileName, e);
        }
    }

    // 파일 확장자를 추출하는 메서드
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 업로드된 이미지의 URL을 반환하는 메서드
    public String getImageUrl(String fileName) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);
    }

}
