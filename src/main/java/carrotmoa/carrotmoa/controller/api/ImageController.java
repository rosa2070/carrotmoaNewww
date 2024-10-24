package carrotmoa.carrotmoa.controller.api;

//import carrotmoa.carrotmoa.service.CommunityImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {

//    final private CommunityImageService imageService;
//
//    @PostMapping("/images/upload")
//    public ResponseEntity<Map<String, Object>> imageUpload(MultipartRequest request) {
//        Map<String, Object> responseData = new HashMap<>();
//
//        try {
//            String s3Url = imageService.imageUpload(request);
//            responseData.put("uploaded", true);
//            responseData.put("url", s3Url);
//            log.info("AWS S3 url 주소: {}", s3Url);
//
//            return new ResponseEntity<>(responseData, HttpStatus.OK);
//        } catch (IOException e) {
//            responseData.put("uploaded", false);
//            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
//        }
//    }
}
