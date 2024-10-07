package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.AccommodationRequest;
import carrotmoa.carrotmoa.model.request.AccommodationSpaceRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RestController// 쓰면 오류
@RequestMapping("/host/room")
@Slf4j
public class HostRoomController {

    // 기본 생성 공간 수 (방, 화장실, 거실, 주방)
    private static final int DEFAULT_SPACE_COUNT = 4;

    // 방 등록 페이지로 이동
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        AccommodationRequest accommodationRequest = new AccommodationRequest();
        accommodationRequest.setUserId(3L);

        // 공간 초기화 메서드 호출
        accommodationRequest.initializeSpaces(DEFAULT_SPACE_COUNT);

        // 수정된 AccommodationRequestDto를 모델에 추가
        model.addAttribute("accommodationRequest", accommodationRequest);
        return "host/room_register";
    }

//    @PostMapping("/register")
//    public String registerRoom(@ModelAttribute AccommodationRequest accommodationRequest) {
//
//        // 로그로 값 확인
//        log.info("Host ID: {}", accommodationRequest.getUserId());
//        log.info("Accommodation Name: {}", accommodationRequest.getName());
//        log.info("Road Address: {}", accommodationRequest.getRoadAddress());
//        log.info("Lot Address: {}", accommodationRequest.getLotAddress());
//        log.info("Detail Address: {}", accommodationRequest.getDetailAddress());
//        log.info("Floor: {}", accommodationRequest.getFloor());
//        log.info("Total Floors: {}", accommodationRequest.getTotalFloor());
//        log.info("Price: {}", accommodationRequest.getPrice());
//        log.info("Details: {}", accommodationRequest.getDetail());
//        log.info("Transportation Info: {}", accommodationRequest.getTransportationInfo());
//        log.info("Selected Amenities IDs: {}", accommodationRequest.getAmenityIds());
//
//        // 이미지 파일 개수 로그
//        List<MultipartFile> images = accommodationRequest.getImages();
//        log.info("Number of uploaded images: {}", (images != null ? images.size() : 0));
//
//        // AccommodationSpaces 로그
//        List<AccommodationSpaceRequest> accommodationSpaces = accommodationRequest.getAccommodationSpaces();
//        if (accommodationSpaces != null) {
//            for (AccommodationSpaceRequest space : accommodationSpaces) {
//                log.info("Accommodation Space - Space ID: {}, Count: {}", space.getSpaceId(), space.getCount());
//            }
//        } else {
//            log.info("No accommodation spaces provided.");
//        }
//
//        return "host/success";
//    }
}
