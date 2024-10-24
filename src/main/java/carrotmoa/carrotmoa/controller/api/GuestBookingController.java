package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.ReservationResponse;
import carrotmoa.carrotmoa.service.ReservationListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/guest/booking/")
public class GuestBookingController {
    @Autowired
    ReservationListService reservationListService;

//    @GetMapping("/{id}")
//    public List<ReservationResponse> getContract(@RequestParam("id") long id, Model model) {
//        List<ReservationResponse> reservations = reservationListService.getUserReservations(id);
//        model.addAttribute("reservations", reservations);
//        return reservationListService.getUserReservations(id);
//    }

    // postman test
//    @GetMapping("/{id}")
//    public List<ReservationResponse> getContract(@PathVariable("id") Long id) {
//        List<ReservationResponse> result = reservationListService.getUserReservations(id);
//        return result;
//    }
}
