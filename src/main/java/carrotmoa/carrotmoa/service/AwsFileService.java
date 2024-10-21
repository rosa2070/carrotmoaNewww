//package carrotmoa.carrotmoa.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@Transactional
//@Slf4j
//public class AwsFileService {
//
//    private final AmazonS3 amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private static final String ROOM_IMG_DIR = "room/";
//
//    public AwsFileService(AmazonS3 amazonS3Client) {
//        this.amazonS3Client = amazonS3Client;
//    }
//
//    public String saveRoomImg(MultipartFile multipartFile, Long roomId) throws IOException {
//        return saveFile(multipartFile, ROOM_IMG_DIR, roomId);
//    }
//
//    public String saveFile(MultipartFile multipartFile, String dirName, Long id) throws IOException {
//        // MultipartFile을 File로 변환하고 실패 시 예외 발생
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
//        return upload(uploadFile, dirName, id); // 변환된 파일을 S3에 업로드
//    }
//
//    private String upload(File uploadFile, String dirName, Long id) {
//        // S3에 저장할 파일 이름 생성
//        String fileName = dirName + id + "/" + UUID.randomUUID() + getFileExtension(uploadFile.getName());
//        String uploadImageUrl = putS3(uploadFile, fileName); // 파일을 S3에 업로드하고 URL 반환
//        removeNewFile(uploadFile); // 로컬 파일 삭제
//        return uploadImageUrl; // 업로드된 이미지 URL 반환
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(bucket, fileName, uploadFile); // s3에 파일 업로드
//        String url = amazonS3Client.getUrl(bucket, fileName).toString();// 업로드된 파일의 URL 생성
//        log.info("Uploaded file to S3: {}", url); // 로그에 업로드 정보 기록
//        return url; // URL 반환
//    }
//
//    private void removeNewFile(File targetFile) {
//        // 로컬에 저장된 파일 삭제
//        if (targetFile.delete()) {
//            log.info("File delete success: {}", targetFile.getName()); // 삭제 성공 로그
//        } else {
//            log.warn("File delete fail: {}", targetFile.getName()); // 삭제 실패 로그
//        }
//    }
//
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        // MultipartFile을 File로 변환
//        File convertFile = new File(System.getProperty("user.home") + "/" + file.getOriginalFilename());
//        if (convertFile.createNewFile()) { // 새 파일 생성 성공 시
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // 파일 출력 스트림 생성
//                fos.write(file.getBytes()); // 파일의 바이트를 출력
//            }
//            return Optional.of(convertFile); // 변환된 파일 반환
//        }
//        return Optional.empty(); // 변환 실패 시 빈 Optional 반환
//    }
//
//    private String getFileExtension(String fileName) {
//        // 파일 이름에서 확장자 추출
//        return fileName.substring(fileName.lastIndexOf("."));
//    }
//
//    public void createDir(String bucketName, String folderName) {
//        // S3 버킷에 디렉토리 생성
//        amazonS3Client.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
//    }
//
//
//}
