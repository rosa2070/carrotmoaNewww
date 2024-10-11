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
        accommodationRequest.logRequestDetails(); // 입력한 거 로그 찍기

        Long accommodationId = accommodationHostService.createAccommodation(accommodationRequest);
        return new ResponseEntity<>(accommodationId, HttpStatus.CREATED);
    }

}
