package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.Accommodation;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class HostManagedAccommodationResponse {
    private Long id; // 숙소 ID
    private String name; // 숙소 이름
    private String lotAddress; // 지번 주소
    private String detailAddress; // 상세 주소
    private BigDecimal price;
    private String imageUrl; // 이미지 URL

    // Object[]를 HostManagedAccommodationResponse로 변환하는 정적 메서드
    public static HostManagedAccommodationResponse fromData(Object[] data, Long accommodationId) {
        return HostManagedAccommodationResponse.builder()
                .id(accommodationId)
                .name((String) data[0])
                .lotAddress((String) data[1])
                .detailAddress((String) data[2])
                .price((BigDecimal) data[3])
                .imageUrl((String) data[4])
                .build();

    }


}
