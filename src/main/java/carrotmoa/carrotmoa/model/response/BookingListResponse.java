package carrotmoa.carrotmoa.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@Slf4j
@Builder
public class BookingListResponse {
    private Long accommodationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int status;

    public static BookingListResponse fromData(Object[] data) {
        return BookingListResponse.builder()
                .accommodationId((Long) data[0])
                .checkInDate((LocalDate) data[1])
                .checkOutDate((LocalDate) data[2])
                .status((Integer) data[3])
                .build();
    }
}
