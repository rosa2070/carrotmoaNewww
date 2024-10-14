package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.Accommodation;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class HostManagedAccommodationResponse {
    private Long id; // 숙소 ID (있어야..?)
    private String name; // 숙소 이름
    private String lotAddress; // 지번 주소
    private String detailAddress; // 상세 주소
    private BigDecimal price; // 가격
    private String imageUrl; // 이미지 URL

    // Object[]를 HostManagedAccommodationResponse로 변환하는 정적 메서드
    public static HostManagedAccommodationResponse fromData(Object[] data) {
        return HostManagedAccommodationResponse.builder()
                .id((Long) data[0])
                .name((String) data[1])
                .lotAddress((String) data[2])
                .detailAddress((String) data[3])
                .price((BigDecimal) data[4])
                .imageUrl((String) data[5])
                .build();
    }


}
