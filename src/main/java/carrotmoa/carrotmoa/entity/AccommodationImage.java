package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "accommodation_image")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationImage extends BaseEntity {

    @Column(name = "accommodation_id")
    private Long accommodationId; // 숙소 ID

    @Column(name = "image_url")
    private String imageUrl; // 이미지 URL
}
