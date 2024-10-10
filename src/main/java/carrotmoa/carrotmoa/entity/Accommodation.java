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
    private Long userId; // 호스트 ID

    @Column(name = "name")
    private String name; // 숙소 이름

    @Column(name = "total_area")
    private Integer totalArea; // 총 면적

    @Column(name = "road_address")
    private String roadAddress; // 도로명 주소

    @Column(name = "lot_address")
    private String lotAddress; // 지번 주소

    @Column(name = "detail_address")
    private String detailAddress; // 상세 주소

    @Column(name = "floor")
    private Integer floor; // 현재 층 정보

    @Column(name = "total_floor")
    private Integer totalFloor; // 총 층 정보

    @Column(name = "price")
    private BigDecimal price; // 숙소 1박 가격

    @Column(name = "detail")
    private String detail; // 숙소에 대한 상세 정보 (소개, 이용 방법, 방 몇개, 화장실 몇개)

    @Column(name = "transportation_info")
    private String transportationInfo; // 교통 정보
}