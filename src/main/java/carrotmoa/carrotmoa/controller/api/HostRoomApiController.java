package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.AccommodationRequest;
import carrotmoa.carrotmoa.model.request.AccommodationSpaceRequest;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
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

    // 방 등록 폼 제출 후 테이블에 값 들어옴
    @PostMapping("/register")
    public ResponseEntity<Long> registerAccommodation(@ModelAttribute AccommodationRequest accommodationRequest) {
        // 페이지 이동은 자바스크립트에서 하는게 좋 (api Controller는 api 받는거만 할수있도록)
        accommodationRequest.logRequestDetails(); // 입력한 거 로그 찍기

        Long accommodationId = accommodationHostService.createAccommodation(accommodationRequest);
        return new ResponseEntity<>(accommodationId, HttpStatus.CREATED);
    }


    // 호스트가 등록한 방 리스트 보이기
    @GetMapping("/manage/{userId}")
    public ResponseEntity<List<HostManagedAccommodationResponse>> getManagedAccommodations(@PathVariable("userId") Long userId) {
        List<HostManagedAccommodationResponse> accommodations = accommodationHostService.getManagedAccommodationsByUserId(userId);
        return ResponseEntity.ok(accommodations);
    }


}
