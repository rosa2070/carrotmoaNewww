package carrotmoa.carrotmoa.controller.api;

//import carrotmoa.carrotmoa.service.ReservationListService;
import carrotmoa.carrotmoa.model.response.ContractDetailResponse;
import carrotmoa.carrotmoa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
@RequestMapping("/guest/booking/")
public class GuestBookingController {
    @Autowired
    ReservationService reservationService;

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
    @GetMapping("/start/{id}")
    public ContractDetailResponse getContractDeatil(@PathVariable("id") Long id) {
        return reservationService.getContractDeatil(id);
    }
}
