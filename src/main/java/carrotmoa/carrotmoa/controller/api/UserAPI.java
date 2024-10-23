package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPI {
    private final UserService userService;

    public UserAPI(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/user/email-check")
    public ResponseEntity<Boolean> emailCheck(@RequestParam("email") String email) {
        return new ResponseEntity<Boolean>(userService.emailCheck(email), HttpStatus.OK);
    }

    @GetMapping("/user/auth-code-send")
    public ResponseEntity<Boolean> authCodeEmailSend(@RequestParam("email") String email) {
        boolean result;
        System.out.println(email);
        try {
            userService.sendCodeToEmail(email);
            result = true;
        } catch (MailSendException e) {
            result = false;
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @GetMapping("/user/auth-code-certified")
    public ResponseEntity<Boolean> authCodeCertified(@RequestParam("email") String email, @RequestParam("inputauthcode") String inputAuthCode) {
        boolean result = userService.authCodeCertified(email, inputAuthCode);
        System.out.println(email);
        System.out.println(inputAuthCode);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }


}
