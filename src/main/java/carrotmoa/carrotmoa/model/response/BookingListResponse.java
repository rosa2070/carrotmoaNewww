package carrotmoa.carrotmoa.model.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Slf4j
public class BookingListResponse {
    // reservation table
    private Long accommodationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer status;
    private BigDecimal totalPrice;
    // post table
    private String title;
    // accommodation table
    private String lotAddress;
    private String detailAddress;
    private Integer floor;
    // accommodationImage - 보류
    private String imageUrl;
    // userProfile
    private String nickname;

    public static BookingListResponse fromData(Object[] data) {
        return BookingListResponse.builder()
                .accommodationId((Long) data[0])
                .checkInDate((LocalDate) data[1])
                .checkOutDate((LocalDate) data[2])
                .status((Integer) data[3])
                .totalPrice((BigDecimal) data[4])
                .title((String) data[5])
                .lotAddress((String) data[6])
                .detailAddress((String) data[7])
                .floor((Integer) data[8])
                .nickname((String) data[9])
                .imageUrl((String) data[10])
                .build();
    }
}
