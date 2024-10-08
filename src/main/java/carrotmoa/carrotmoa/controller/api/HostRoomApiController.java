package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.AccommodationRequest;
import carrotmoa.carrotmoa.model.request.AccommodationSpaceRequest;
import carrotmoa.carrotmoa.service.AccommodationHostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/host/room")
public class HostRoomApiController {

    @Autowired
    AccommodationHostService accommodationHostService;

    @PostMapping("/register")
//    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> registerAccommodation(@ModelAttribute AccommodationRequest accommodationRequest) {
        // 페이지 이동은 자바스크립트에서 하는게 좋 (api Controller는 api 받는거만 할수있도록)
        // service로 따로 빼거나
//        accommodationRequest에 따로 빽?
        //        // 로그로 값 확인
        log.info("Host ID: {}", accommodationRequest.getUserId());
        log.info("Accommodation Name: {}", accommodationRequest.getName());
        log.info("Road Address: {}", accommodationRequest.getRoadAddress());
        log.info("Lot Address: {}", accommodationRequest.getLotAddress());
        log.info("Detail Address: {}", accommodationRequest.getDetailAddress());
        log.info("Floor: {}", accommodationRequest.getFloor());
        log.info("Total Floors: {}", accommodationRequest.getTotalFloor());
        log.info("Price: {}", accommodationRequest.getPrice());
        log.info("Details: {}", accommodationRequest.getDetail());
        log.info("Transportation Info: {}", accommodationRequest.getTransportationInfo());
        log.info("Selected Amenities IDs: {}", accommodationRequest.getAmenityIds());

        // 이미지 파일 개수 로그
        List<MultipartFile> images = accommodationRequest.getImages();
        log.info("Number of uploaded images: {}", (images != null ? images.size() : 0));

        // AccommodationSpaces 로그
        List<AccommodationSpaceRequest> accommodationSpaces = accommodationRequest.getAccommodationSpaces();
        if (accommodationSpaces != null) {
            for (AccommodationSpaceRequest space : accommodationSpaces) {
                log.info("Accommodation Space - Space ID: {}, Count: {}", space.getSpaceId(), space.getCount());
            }
        } else {
            log.info("No accommodation spaces provided.");
        }


        Long accommodationId = accommodationHostService.createAccommodation(accommodationRequest);
        return new ResponseEntity<>(accommodationId, HttpStatus.CREATED);
    }

}
