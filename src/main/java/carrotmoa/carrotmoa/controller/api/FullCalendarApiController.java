package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.BookingListResponse;
import carrotmoa.carrotmoa.model.response.FullCalendarResponse;
import carrotmoa.carrotmoa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guest/booking")
public class FullCalendarApiController {
    @Autowired
    ReservationService reservationService;

    @GetMapping("/{id}")
    public List<FullCalendarResponse> getBookingDates(@PathVariable("id") Long id) {
        List<FullCalendarResponse> bookings = reservationService.getBookedDates(id);
        return bookings;
    }
}
