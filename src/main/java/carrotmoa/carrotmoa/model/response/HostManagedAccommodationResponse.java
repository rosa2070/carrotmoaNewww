package carrotmoa.carrotmoa.model.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class HostManagedAccommodationResponse {
    private Long id;
    private String title;
    private String lotAddress;
    private String detailAddress;
    private BigDecimal price;
    private String imageUrl;


}
