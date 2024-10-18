package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.model.request.AccommodationSpaceRequest;
import carrotmoa.carrotmoa.model.request.HostAccommodationRequest;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import carrotmoa.carrotmoa.service.AccommodationHostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/host/room")
public class HostRoomApiController {
    // 기본 생성 공간 수 (방, 화장실, 거실, 주방)
    private static final int DEFAULT_SPACE_COUNT = 4;

    @Autowired
    AccommodationHostService accommodationHostService;

    // 방 등록 폼 제출 후 테이블에 값 들어옴
    @PostMapping("/register")
    public ResponseEntity<Long> registerAccommodation(@ModelAttribute HostAccommodationRequest hostAccommodationRequest) {
        // 공간 초기화 메서드 호출
        hostAccommodationRequest.initializeSpaces(DEFAULT_SPACE_COUNT);
        hostAccommodationRequest.setUserId(3L);

        // 입력한 거 로그 찍기
        hostAccommodationRequest.logRequestDetails();

        Long accommodationId = accommodationHostService.createAccommodation(hostAccommodationRequest);
        return new ResponseEntity<>(accommodationId, HttpStatus.CREATED);
    }


    // 방 상세
    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDetailResponse> getAccommodationDetail(@PathVariable("id") Long id) {
        AccommodationDetailResponse accommodationDetailResponse = accommodationHostService.getAccommodationDetail(id);
        if (accommodationDetailResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accommodationDetailResponse);
    }



    // 호스트가 등록한 방 리스트 보이기
    @GetMapping("/manage/{userId}")
    public ResponseEntity<List<HostManagedAccommodationResponse>> getAccommodationsByUserId(@PathVariable("userId") Long userId) {
        List<HostManagedAccommodationResponse> accommodations = accommodationHostService.getAccommodationsByUserId(userId);

        if (accommodations == null || accommodations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }

        return ResponseEntity.ok(accommodations);
    }


}
