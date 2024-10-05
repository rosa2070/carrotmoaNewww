package carrotmoa.carrotmoa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccommodationRequestDto {
    // id없어도..?
    private Long userId; //호스트 ID
    private String name; // 숙소 이름
    private String roadAddress; // 도로명 주소
    private String lotAddress;
    private String detailAddress;
    private Integer floor;
    private Integer totalFloor;
    private BigDecimal price;
    private String detail;
    private String transportationInfo;

    // 관계 테이블 리스트 (방, 화장실, 거실, 주방 순서로 담겨야)
    private List<AccommodationSpaceDto> accommodationSpaces = new ArrayList<>(); // 초기화
    private List<Long> amenityIds; // 선택된 편의시설 IDs
    private List<MultipartFile> images; // 업로드된 이미지 파일들

}
