package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Column(name = "user_id")
    private Long userId; // 게스트 ID

    @Column(name = "accommodation_id")
    private Long accommodationId; // 숙소 ID

    @Column(name = "check_in_date")
    private LocalDate checkInDate; // 체크인 날짜

    @Column(name = "check_out_date")
    private LocalDate checkOutDate; // 체크아웃 날짜

    @Column(name = "total_price")
    private BigDecimal totalPrice; // 총 가격

    @Column(name = "status")
    private Integer status; // 예약 상태

}
