package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.Accommodation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class HostManagedAccommodationResponse {
    private Long id; // 숙소 ID (있어야..?)
    private String title;
    private String lotAddress; // 지번 주소
    private String detailAddress; // 상세 주소
    private BigDecimal price; // 가격
    private String imageUrl;

    @QueryProjection
    public HostManagedAccommodationResponse(Long id, String title, String lotAddress, String detailAddress, BigDecimal price) {
        this.id = id;
        this.lotAddress = lotAddress;
        this.detailAddress = detailAddress;
        this.price = price;

    }


}
