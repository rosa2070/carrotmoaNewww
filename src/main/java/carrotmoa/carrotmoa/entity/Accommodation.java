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

    @Column(name = "post_id")
    private Long postId; // 포스트 ID

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

    @Column(name = "transportation_info")
    private String transportationInfo; // 교통 정보

    // 업데이트 메서드
    public void updateAccommodation(Integer totalArea, String roadAddress, String lotAddress,
                                    String detailAddress, Integer floor, Integer totalFloor,
                                    BigDecimal price, String transportationInfo) {
        if (totalArea != null) {
            this.totalArea = totalArea;
        }
        if (roadAddress != null) {
            this.roadAddress = roadAddress;
        }
        if (lotAddress != null) {
            this.lotAddress = lotAddress;
        }
        if (detailAddress != null) {
            this.detailAddress = detailAddress;
        }
        if (floor != null) {
            this.floor = floor;
        }
        if (totalFloor != null) {
            this.totalFloor = totalFloor;
        }
        if (price != null) {
            this.price = price;
        }
        if (transportationInfo != null) {
            this.transportationInfo = transportationInfo;
        }
    }

}