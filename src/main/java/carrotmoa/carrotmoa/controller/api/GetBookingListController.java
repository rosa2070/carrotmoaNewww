package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController
@RequestMapping("/guest/booking")
public class GetBookingListController {
    @Autowired
    ReservationService reservationService;

    @GetMapping("/{id}") // postman test
    public List<BookingListResponse> getBookingList(@PathVariable("id") Long id) {
        List<BookingListResponse> bookings = reservationService.getBookingList(id);
        return bookings;
    }
}
