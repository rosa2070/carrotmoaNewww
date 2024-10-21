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
@Table(name = "accommodation_image")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationImage extends BaseEntity {

    @Column(name = "accommodation_id")
    private Long accommodationId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "save_file_name")
    private String saveFileName;

    @Column(name = "size")
    private Long size;
}
