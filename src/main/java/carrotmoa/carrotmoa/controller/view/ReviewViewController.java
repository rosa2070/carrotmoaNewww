package carrotmoa.carrotmoa.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guest/review")
public class ReviewViewController {
    @GetMapping("/{reservationId}")
    public String gotoReviewView(@PathVariable("reservationId") Long reservationId) {
//        System.out.println(reservationId);
        return "guest/review";
    }
}
