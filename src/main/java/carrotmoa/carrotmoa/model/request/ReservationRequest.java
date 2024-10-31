package carrotmoa.carrotmoa.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private Long userId;
    private Long accommodationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private
}
