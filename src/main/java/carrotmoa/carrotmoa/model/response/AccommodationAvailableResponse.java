package carrotmoa.carrotmoa.model.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AccommodationAvailableResponse {
    private Long id;
    private String title;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String lotAddress;
    private String imageUrl;

    public static AccommodationAvailableResponse fromData(Object[] data) {

        return AccommodationAvailableResponse.builder()
                .id((Long) data[0])
                .title((String) data[1])
                .latitude((BigDecimal) data[2])
                .longitude((BigDecimal) data[3])
                .lotAddress((String) data[4])
                .imageUrl((String) data[5])
                .build();
    }
}
