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
@Table(name = "accommodation_spaces")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationSpaces extends BaseEntity {

    @Column(name = "accommodation_id")
    private Long accommodationId; // 숙소 ID

    @Column(name = "space_type")
    private String spaceType; // 공간 유형 (방, 화장실, 거실, 주방)

    @Column(name = "space_icon")
    private String spaceIcon; // 공간 아이콘 URL

    @Column(name = "count")
    private Integer count; // 공간 개수

}
