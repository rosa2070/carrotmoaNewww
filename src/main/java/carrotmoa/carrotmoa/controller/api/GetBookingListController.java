package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController
@RequestMapping("/guest/booking/list")
public class GetBookingListController {
    @Autowired
    ReservationService reservationService;

//    @GetMapping("/{id}") // postman test
//    public List<BookingListResponse> getList(@PathVariable("id") Long id) {
//        List<BookingListResponse> bookings = reservationService.getBookingList(id);;
//        return bookings;
//    }

    @GetMapping("/{id}")
    public String getBookingList(@PathVariable("id") Long id, Model model) {
        List<BookingListResponse> bookings = reservationService.getBookingList(id); // userId

        model.addAttribute("bookings", bookings);
        return "guest/bookingList";
    }
}
