package carrotmoa.carrotmoa.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host/room")
public class HostRoomController {

    // 방 등록 페이지로 이동
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "host/room_register";
    }
}
