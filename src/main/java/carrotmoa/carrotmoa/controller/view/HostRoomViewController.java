package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.model.request.HostAccommodationRequest;
import carrotmoa.carrotmoa.service.AccommodationHostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Controller
//@RestController// 쓰면 오류
@RequestMapping("/host/room")
@Slf4j
public class HostRoomViewController {

    // 기본 생성 공간 수 (방, 화장실, 거실, 주방)
    private static final int DEFAULT_SPACE_COUNT = 4;
    private final AccommodationHostService accommodationHostService;

    public HostRoomViewController(AccommodationHostService accommodationHostService) {
        this.accommodationHostService = accommodationHostService;
    }

    // 방 등록 페이지로 이동
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        HostAccommodationRequest hostAccommodationRequest = new HostAccommodationRequest();
//        hostAccommodationRequest.setUserId(3L);

        // 공간 초기화 메서드 호출
        hostAccommodationRequest.initializeSpaces(DEFAULT_SPACE_COUNT);

        // 수정된 AccommodationRequestDto를 모델에 추가
        model.addAttribute("hostAccommodationRequest", hostAccommodationRequest);
        return "host/room_register";
    }


    // 방 관리 이동
    @GetMapping("/manage")
    public String getRoomManage() {
        return "host/room_manage";
    }

    // 방 수정
    @GetMapping("/edit/{accommodationId}")
    public String showEditRoomForm(@PathVariable("accommodationId") Long accommodationId) {
        return "host/room_edit";
    }
}
