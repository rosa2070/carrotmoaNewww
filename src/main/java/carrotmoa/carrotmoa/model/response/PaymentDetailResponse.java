package carrotmoa.carrotmoa.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailResponse {
    private LocalDate settlementDate; // 체크인 날짜 + 1
    private String title;
    private String name; // 유저 이름
    private LocalDate checkInDate;
    private BigDecimal paymentAmount;
}
