package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.config.S3Config;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommunityImageService {
    final private S3Config s3Config;
    private String localLocation = "C:\\Users\\user\\Desktop\\localfileupload\\";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String imageUpload(MultipartRequest request) throws IOException {

        // request 인자에서 이미지 파일 가져옴.
        MultipartFile file =request.getFile("upload");

        // 가져온 이미지 파일에서 이름 및 확장자 추출
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));

        // 이미지 파일명 유일성을 위해 uuid 생성
        String uuidFileName = UUID.randomUUID() + ext;

        // 서버 환경에 저장할 경로 생성
        String localPath = localLocation + uuidFileName;

        // 서버 환경에 이미지 파일 저장
        File localFile = new File(localPath);
        file.transferTo(localFile);

        // s3에 이미지 올리는 코드
        s3Config.s3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.s3Client().getUrl(bucket, uuidFileName).toString();

        // 서버에 저장한 이미지 삭제
        localFile.delete();
        return s3Url;
    }
}
