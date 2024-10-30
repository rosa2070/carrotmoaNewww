package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.AccommodationImageListResponse;
import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController
@RequestMapping("/guest/booking")
public class GetBookingListController {
    @Autowired
    ReservationService reservationService;

//    @GetMapping("/{id}") // postman test
//    public List<AccommodationImageListResponse> getList(@PathVariable("id") Long id) {
//        List<AccommodationImageListResponse> images = reservationService.getAccommodationImageList(id);
//        return images;
//    }

    @GetMapping("/list")
    public String getBookingList(@RequestParam("id") Long id, Model model) {
        List<BookingListResponse> bookings = reservationService.getBookingList(id);
        model.addAttribute("bookings", bookings);
        return "guest/bookingList";
    }
}
