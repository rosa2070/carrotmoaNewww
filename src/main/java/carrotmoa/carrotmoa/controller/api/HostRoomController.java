package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.AccommodationRequest;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import carrotmoa.carrotmoa.service.AccommodationHostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
//@RestController// 쓰면 오류
@RequestMapping("/host/room")
@Slf4j
public class HostRoomController {

    // 기본 생성 공간 수 (방, 화장실, 거실, 주방)
    private static final int DEFAULT_SPACE_COUNT = 4;
    private final AccommodationHostService accommodationHostService;

    public HostRoomController(AccommodationHostService accommodationHostService) {
        this.accommodationHostService = accommodationHostService;
    }

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

    // 방 목록 메인 이동 테스트
    @GetMapping("/main")
    public String showHostMain() {
        return "host/host_main";
    }


    // 방 관리(메인) 이동
//    @GetMapping("/manage")
//    public String getManagedAccommodations(Model model) {
//        Long userId = 3L; // 임의의 userId 설정
//        List<HostManagedAccommodationResponse> accommodations = accommodationHostService.getManagedAccommodationsByUserId(userId);
//        model.addAttribute("accommodations", accommodations);
//        return "host/host_manage";
//    }

    @GetMapping("/manage")
    public String getRoomManage() {
        return "host/host_manage";
    }
}
