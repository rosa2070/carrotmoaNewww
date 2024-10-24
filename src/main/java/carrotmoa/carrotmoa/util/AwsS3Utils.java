package carrotmoa.carrotmoa.util;


import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Utils {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

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

    // S3에서 이미지를 삭제하는 메서드
//    public void deleteImage(String fileName) throws IOException {
//        // S3에서 객체 삭제 요청 생성
//        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                .bucket(bucketName) // 버킷 이름
//                .key(fileName) // 파일 이름
//                .build();
//
//        try {
//            s3Client.deleteObject(deleteObjectRequest); // S3에서 객체 삭제
//            log.info("Deleted image: {}", fileName); // 삭제 성공 로그
//        } catch (SdkException e) {
//            log.error("Error deleting file from S3: {}", fileName, e); // 삭제 실패 로그
//            throw new IOException("Error deleting file from S3: " + fileName); // 예외 발생
//        }
//    }

    // S3에서 이미지를 삭제하는 메서드
//    public void deleteImageFromUrl(String url) throws IOException {
//        // S3 URL에서 fileName 추출
//        String fileName = extractFileName(url);
//
//        // S3에서 객체 삭제 요청 생성
//        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                .bucket(bucketName) // 버킷 이름
//                .key(fileName) // 파일 이름
//                .build();
//
//        try {
//            s3Client.deleteObject(deleteObjectRequest); // S3에서 객체 삭제
//            log.info("Deleted image: {}", fileName); // 삭제 성공 로그
//        } catch (SdkException e) {
//            log.error("Error deleting file from S3: {}", fileName, e); // 삭제 실패 로그
//            throw new IOException("Error deleting file from S3: " + fileName, e); // 예외 발생
//        }
//    }

    // 파일 확장자를 추출하는 메서드
    public String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 업로드된 이미지의 URL을 반환하는 메서드
    public String getImageUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }

    // 삭제 구현할 때 url에서 fileName 분리
//    public String extractFileName(String url) {
//        // URL을 슬래시('/')로 분리
//        String[] parts = url.split("/");
//        // 마지막 부분이 파일 이름
//        return parts[parts.length - 1];
//    }

    public void deleteImageFromUrl(String url) throws IOException {
        // URL에서 파일 경로 추출
        String filePath = extractFilePathFromUrl(url);

        // S3에서 객체 삭제 요청 생성
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName) // 버킷 이름
            .key(filePath) // 파일 경로
            .build();

        try {
            s3Client.deleteObject(deleteObjectRequest); // S3에서 객체 삭제
            log.info("Deleted image: {}", filePath); // 삭제 성공 로그
        } catch (SdkException e) {
            log.error("Error deleting file from S3: {}", filePath, e); // 삭제 실패 로그
            throw new IOException("Error deleting file from S3: " + filePath, e); // 예외 발생
        }
    }

    String extractFilePathFromUrl(String url) {
        // URL을 슬래시('/')로 분리
        String[] parts = url.split("/");

        // room/42/와 파일 이름을 결합
        return parts[parts.length - 3] + "/" + parts[parts.length - 2] + "/" + parts[parts.length - 1];
    }


}
