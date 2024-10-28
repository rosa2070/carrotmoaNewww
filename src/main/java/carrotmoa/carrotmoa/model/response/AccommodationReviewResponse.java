package carrotmoa.carrotmoa.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Slf4j
public class AccommodationReviewResponse {
    private Long id;
    private Long userId;
    private int rating;
    private String comment;
    private LocalDate updatedAt;
    private String nickname;

    public static AccommodationReviewResponse fromData(Object[] data){
        return AccommodationReviewResponse.builder()
                .id((Long) data[0])
                .userId((Long) data[1])
                .rating((int) data[2])
                .comment((String) data[3])
                .updatedAt(((LocalDateTime) data[4]).toLocalDate())
                .nickname((String) data[5])
                .build();
    }
}
