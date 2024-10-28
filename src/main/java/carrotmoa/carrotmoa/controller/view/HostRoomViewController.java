package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.config.KakaoMapConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host/room")
@Slf4j
public class HostRoomViewController {

    private final KakaoMapConfig kakaoMapConfig;

    @Autowired
    public HostRoomViewController(KakaoMapConfig kakaoMapConfig) {
        this.kakaoMapConfig = kakaoMapConfig;
    }

    // 방 등록
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        String kakaoMapSdkUrl = kakaoMapConfig.getSdkUrl(); // 설정 값 가져오기
        model.addAttribute("kakaoMapSdkUrl", kakaoMapSdkUrl);
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
