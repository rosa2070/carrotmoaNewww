package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.config.security.CustomUserDetails;
import carrotmoa.carrotmoa.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/wishList")
public class WishListApiController {
    @Autowired
    private WishListService wishListService;

    @PostMapping("/wishList/add")
    @ResponseBody
    public ResponseEntity<?> addWishList(@ModelAttribute("user") CustomUserDetails user, @RequestParam("id") Long id) { // accommodationId
        Long userId = user.getUserProfile().getUserId();
        wishListService.updateWishList(userId, id);
        return ResponseEntity.ok().build();
    }
}
