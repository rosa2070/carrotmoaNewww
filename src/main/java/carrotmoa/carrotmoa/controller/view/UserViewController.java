package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.model.request.UserJoinDto;
import carrotmoa.carrotmoa.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserViewController {
    UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login-page")
    public String userLoginForm() {
        return "user/login-page";
    }

    @GetMapping("/join-form")
    public String userJoinForm(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "user/join";
    }

    @GetMapping("/my-page")
    public String userMyPage() {
        return "user/my-page";
    }


    //css디자인 작성용
    @GetMapping("/test")
    public String test() {
        return "user/test";
    }

}


