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
    private Long accommodationId; // 숙소 ID

    @Column(name = "image_url")
    private String imageUrl; // 이미지 URL

    @Column(name = "original_name")
    private String originalName; // 원본 파일 이름

    @Column(name = "save_file_name")
    private String saveFileName; // 디스크에 저장된 파일명

    @Column(name = "size")
    private Long size; // 파일의 크기

}
