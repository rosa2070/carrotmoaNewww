package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.config.security.CustomUserDetails;
import carrotmoa.carrotmoa.model.request.UserJoinDto;
import carrotmoa.carrotmoa.model.request.UserUpdateRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserViewController {

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
        return "/user/my-page";
    }

    @GetMapping("/password-update")
    public String userUpdateForm() {return "user/password-update";}

    @GetMapping("/profile-update")
    public String userUpdateProfileForm(@AuthenticationPrincipal CustomUserDetails user,Model model) {
        model.addAttribute("userUpdateRequestDto", new UserUpdateRequest(user));
        return "user/profile-update";}
}


