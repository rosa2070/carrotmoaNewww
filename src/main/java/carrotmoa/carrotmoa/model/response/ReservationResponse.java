//package carrotmoa.carrotmoa.model.response;
//
//import carrotmoa.carrotmoa.entity.Reservation;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Builder
//public class ReservationResponse {
//    private List<Long> id;
//    private List<Long> userId;
//    private Long accommodationId;
//    private List<LocalDate> checkInDate;
//    private List<LocalDate> checkOutDate;
//    private List<BigDecimal> totalPrice;
//    private List<Integer> status;
//    private String lotAddress;
//    private String detailAddress;
//    private String imageUrl;
//    private String title;
//
//    public static ReservationResponse fromData(Reservation reservation, Object[] data) {
//        return ReservationResponse.builder()
//            .id(reservation.getId())
//            .userId(reservation.getUserId())
//            .accommodationId(reservation.getAccommodationId())
//            .status(reservation.getStatus())
//            .checkInDate(reservation.getCheckInDate())
//            .checkOutDate(reservation.getCheckOutDate())
//            .totalPrice(reservation.getTotalPrice())
//            .lotAddress((String) data[9])
//            .detailAddress((String) data[10])
//            .imageUrl((String) data[11])
//            .title((String) data[12])
//            .build();
//    }
//}
