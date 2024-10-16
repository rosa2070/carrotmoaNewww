package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.AccommodationSpace;
import carrotmoa.carrotmoa.model.request.AccommodationSpaceRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccommodationResponse {
    private Long id; // 숙소 ID
    private Long userId; // 호스트 ID
    private String name; // 숙소 이름
    private Integer totalArea; // 총 면적
    private String roadAddress; // 도로명 주소
    private String lotAddress; // 지번 주소
    private String detailAddress; // 상세 주소
    private Integer floor; // 층수
    private Integer totalFloor; // 총 층수
    private BigDecimal price; // 가격
    private String detail; // 방 설명
    private String transportationInfo; // 교통편
    private List<AccommodationSpace> accommodationSpaces; // 숙소 공간 리스트
    private List<Long> amenityIds; // 편의시설 ID 리스트

}
