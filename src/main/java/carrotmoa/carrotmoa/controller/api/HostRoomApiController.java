package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.HostAccommodationRequest;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import carrotmoa.carrotmoa.service.AccommodationHostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/host/room")
public class HostRoomApiController {

    @Autowired
    AccommodationHostService accommodationHostService;

    // 방 등록 폼 제출 후 테이블에 값 들어옴
    @PostMapping("/register")
    public ResponseEntity<Long> registerAccommodation(@ModelAttribute HostAccommodationRequest hostAccommodationRequest) {
        // 페이지 이동은 자바스크립트에서 하는게 좋 (api Controller는 api 받는거만 할수있도록)
        hostAccommodationRequest.setUserId(3L);
        hostAccommodationRequest.logRequestDetails(); // 입력한 거 로그 찍기


        Long accommodationId = accommodationHostService.createAccommodation(hostAccommodationRequest);
        return new ResponseEntity<>(accommodationId, HttpStatus.CREATED);
    }


    // 호스트가 등록한 방 리스트 보이기
//    @GetMapping("/manage/{userId}")
//    public ResponseEntity<List<HostManagedAccommodationResponse>> getManagedAccommodations(@PathVariable("userId") Long userId) {
//        List<HostManagedAccommodationResponse> accommodations = accommodationHostService.getManagedAccommodationsByUserId(userId);
//        return ResponseEntity.ok(accommodations);
//    }

    // 수정을 위해 방 가져오기
//    @GetMapping("/manage/{accommodationId}")
//    public ResponseEntity<>


}
