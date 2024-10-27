package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.UserJoinDto;
import carrotmoa.carrotmoa.model.request.UserUpdateRequest;
import carrotmoa.carrotmoa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/email-check")
    public ResponseEntity<Boolean> emailCheck(@RequestParam("email") String email) {
        return new ResponseEntity<Boolean>(userService.emailCheck(email), HttpStatus.OK);
    }

    @GetMapping("/auth-code-send")
    public ResponseEntity<Boolean> authCodeEmailSend(@RequestParam("email") String email) {
        boolean result;
        try {
            userService.sendCodeToEmail(email);
            result = true;
        } catch (MailSendException e) {
            result = false;
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @GetMapping("/auth-code-certified")
    public ResponseEntity<Boolean> authCodeCertified(@RequestParam("email") String email, @RequestParam("inputauthcode") String inputAuthCode) {
        boolean result = userService.authCodeCertified(email, inputAuthCode);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<Boolean> userJoinSubmit(@RequestBody UserJoinDto userJoinDto) {
        boolean result = userService.userJoin(userJoinDto);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
    @GetMapping("/nickname-duplication")
    public ResponseEntity<Boolean> nicknameDuplication(@RequestParam("nickname") String nickname) {
        return new ResponseEntity<Boolean>(userService.nicknameCheck(nickname),HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> userProfileUpdate(@RequestBody UserUpdateRequest userUpdateRequestDto){
        System.out.println("update 호출");
        System.out.println(userUpdateRequestDto.getNickname());
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }



}
