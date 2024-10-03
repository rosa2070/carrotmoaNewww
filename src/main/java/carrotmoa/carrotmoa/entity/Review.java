package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "review")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    @Column(name = "accommodation_id")
    private Long accommodationId; // 숙소 ID

    @Column(name = "user_id")
    private Long userId; // 게스트 ID

    @Min(1)
    @Max(5)
    @Column(name = "rating")
    private Integer rating; // 별점(1~5)

    @Column(name = "comment")
    private String comment; // 리뷰 내용

    @Column(name = "star_icon")
    private String starIcon; // 별 이미지 URL

}
