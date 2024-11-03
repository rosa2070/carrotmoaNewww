package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.config.KakaoMapConfig;
import carrotmoa.carrotmoa.config.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String showRegisterForm(Model model, @ModelAttribute("user") CustomUserDetails user) {
        if (user == null) {
            // 사용자 정보가 없을 경우 처리
            return "/user/login-page"; // 로그인 페이지로 리디렉션
        }

        String kakaoMapSdkUrl = kakaoMapConfig.getSdkUrl(); // 설정 값 가져오기
        model.addAttribute("kakaoMapSdkUrl", kakaoMapSdkUrl);

        // 로그인한 유저
        model.addAttribute("user", user);
        return "host/room_register";
    }

    // 방 관리 이동
    @GetMapping("/manage")
    public String getRoomManage(@ModelAttribute("user") CustomUserDetails user, Model model) {
        if (user == null) {
            return "/user/login-page";
        }

        model.addAttribute("user", user);
        return "host/room_manage";
    }

    // 방 수정
    @GetMapping("/edit/{accommodationId}")
    public String showEditRoomForm(@PathVariable("accommodationId") Long accommodationId) {
        return "host/room_edit";
    }

    // 정산
    @GetMapping("/settlement")
    public String getSettlements() {
        return "host/room_settlement";
    }

    // 계약
    @GetMapping("/contract")
    public String getContracts(@ModelAttribute("user") CustomUserDetails user, Model model) {
        if (user == null) {
            return "/user/login-page";
        }

        model.addAttribute("user", user);
        return "host/room_contract";
    }
}
