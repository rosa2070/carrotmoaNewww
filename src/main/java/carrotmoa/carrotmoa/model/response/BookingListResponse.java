package carrotmoa.carrotmoa.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class BookingListResponse {
    private Long accommodationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int status;
}
