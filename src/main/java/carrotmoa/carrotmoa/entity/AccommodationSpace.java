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

@Entity
@Table(name = "accommodation_space")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationSpace extends BaseEntity {

    @Column(name = "accommodation_id")
    private Long accommodationId; // 숙소 ID

    @Column(name = "space_id")
    private Long spaceId; // 공간 ID

    @Column(name = "count")
    private Integer count; // 공간 개수

    // 숙소와 공간 간의 관계
    public void updateAccommodationSpace(Integer newCount) {
        this.count = newCount;
    }

}
