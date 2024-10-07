package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.AccommodationAmenity;
import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccommodationRequest {
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
    // 관계 테이블 리스트 (Space 테이블에 방, 화장실, 거실, 주방 순서로 담겨 있어야)
    private List<AccommodationSpaceRequest> accommodationSpaces = new ArrayList<>(); // 초기화
    private List<Long> amenityIds = new ArrayList<>();
    private List<MultipartFile> images; // 업로드된 이미지 파일들


    public Accommodation toAccommodationEntity() {
        return Accommodation.builder()
                .userId(userId)
                .name(name)
                .roadAddress(roadAddress)
                .lotAddress(lotAddress)
                .detailAddress(detailAddress)
                .floor(floor)
                .totalFloor(totalFloor)
                .price(price)
                .detail(detail)
                .transportationInfo(transportationInfo)
                .build();
    }

    public List<AccommodationSpace> toAccommodationSpaceEntities() {
        return accommodationSpaces.stream()
                .map(accommodationSpaceRequest -> {
                    AccommodationSpace accommodationSpace = new AccommodationSpace();
                    accommodationSpace.setAccommodationId(accommodationSpaceRequest.getAccommodationId());
                    accommodationSpace.setSpaceId(accommodationSpaceRequest.getSpaceId());
                    accommodationSpace.setCount(accommodationSpaceRequest.getCount());
                    return accommodationSpace;
                })
                .collect(Collectors.toList());
    }

    // 공간 초기화 메서드
    public void initializeSpaces(int spaceCount) {
        accommodationSpaces.clear(); // 기존 공간 초기화
        for (int i = 0; i < spaceCount; i++) {
            AccommodationSpaceRequest accommodationSpaceRequest = new AccommodationSpaceRequest();
            accommodationSpaceRequest.setSpaceId((long) (i + 1));
            accommodationSpaces.add(accommodationSpaceRequest);
        }
    }

}
