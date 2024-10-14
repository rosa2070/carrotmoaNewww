package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.AccommodationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

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

    @GetMapping("/main")
    public String showHostMain() {
        return "host/host_main";
    }



}
