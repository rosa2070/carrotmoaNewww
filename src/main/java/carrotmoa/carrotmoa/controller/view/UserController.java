package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.config.sequrity.CustomUserDetails;
import carrotmoa.carrotmoa.model.request.UserJoinDto;
import carrotmoa.carrotmoa.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login-page")
    public String userLoginForm() {
        return "user/login-page";
    }

    @GetMapping("/user/login")
    public String userLogin(@AuthenticationPrincipal CustomUserDetails user) {
        if (user != null) {
            System.out.println(user.getUserProfile().getPicUrl());
        }
        return "index";
    }

    @GetMapping("/user/join-form")
    public String userJoinForm(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "user/join";
    }

    @PostMapping("/user/join")
    public String userJoinSubmit(@ModelAttribute("userJoinDto") UserJoinDto userJoinDto) {
        userService.userJoin(userJoinDto);
        return "index";
    }

    @GetMapping("/user/my-page")
    public String userMyPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails);
        return "user/my-page";
    }

    @GetMapping("/test")
    public String test() {
        return "user/test";
    }

}


