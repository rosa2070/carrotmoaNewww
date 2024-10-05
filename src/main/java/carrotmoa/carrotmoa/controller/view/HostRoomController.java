package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.dto.AccommodationRequestDto;
import carrotmoa.carrotmoa.dto.AccommodationSpaceDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/host/room")
@Slf4j
public class HostRoomController {

    // 방 등록 페이지로 이동
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        AccommodationRequestDto accommodationRequestDto = new AccommodationRequestDto();
        accommodationRequestDto.setAccommodationSpaces(new ArrayList<>());

        // 공간 초기화
        for (int i=0; i<4; i++) {
            AccommodationSpaceDto accommodationSpaceDto = new AccommodationSpaceDto();
            accommodationSpaceDto.setSpaceId((long) (i+1)); // 1, 2, 3, 4
            accommodationRequestDto.getAccommodationSpaces().add(accommodationSpaceDto);
        }

        // 수정된 AccommodationRequestDto를 모델에 추가
        model.addAttribute("accommodationRequestDto", accommodationRequestDto);
        return "host/room_register";
    }

    @PostMapping("/register")
    public String registerRoom(@ModelAttribute AccommodationRequestDto accommodationRequestDto) {
        // 로그로 값 확인
        log.info("Host ID: {}", accommodationRequestDto.getUserId());
        log.info("Accommodation Name: {}", accommodationRequestDto.getName());
        log.info("Road Address: {}", accommodationRequestDto.getRoadAddress());
        log.info("Lot Address: {}", accommodationRequestDto.getLotAddress());
        log.info("Detail Address: {}", accommodationRequestDto.getDetailAddress());
        log.info("Floor: {}", accommodationRequestDto.getFloor());
        log.info("Total Floors: {}", accommodationRequestDto.getTotalFloor());
        log.info("Price: {}", accommodationRequestDto.getPrice());
        log.info("Details: {}", accommodationRequestDto.getDetail());
        log.info("Transportation Info: {}", accommodationRequestDto.getTransportationInfo());
        log.info("Selected Amenities IDs: {}", accommodationRequestDto.getAmenityIds());

        // 이미지 파일 개수 로그
        List<MultipartFile> images = accommodationRequestDto.getImages();
        log.info("Number of uploaded images: {}", (images != null ? images.size() : 0));

        // AccommodationSpaces 로그
        List<AccommodationSpaceDto> accommodationSpaces = accommodationRequestDto.getAccommodationSpaces();
        if (accommodationSpaces != null) {
            for (AccommodationSpaceDto space : accommodationSpaces) {
                log.info("Accommodation Space - Space ID: {}, Count: {}", space.getSpaceId(), space.getCount());
            }
        } else {
            log.info("No accommodation spaces provided.");
        }

        return "host/success";
    }
}
