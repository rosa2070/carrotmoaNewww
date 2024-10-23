package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accommodation")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "lot_address")
    private String lotAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "total_floor")
    private Integer total_floor;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "detail")
    private String detail;

    @Column(name = "transportation_info")
    private String transportationInfo;
}
