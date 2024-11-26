package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.BestAccommodationResponse;
import carrotmoa.carrotmoa.service.BestAccommodationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class BestAccommodationController {

    private final BestAccommodationService bestAccommodationService;

    public BestAccommodationController(BestAccommodationService bestAccommodationService) {
        this.bestAccommodationService = bestAccommodationService;
    }

    @GetMapping("/best")
    public ResponseEntity<List<BestAccommodationResponse>> getBestAccommodations() {
        List<BestAccommodationResponse> bestAccommodations = bestAccommodationService.getBestAccommodations();
        return ResponseEntity.ok(bestAccommodations);
//                .contentType(MediaType.APPLICATION_JSON) // Content-Type을 application/json으로 명시
//                .body(bestAccommodations);
    }

}
