package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.*;
import carrotmoa.carrotmoa.model.response.GuestReservationResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDetailCustomRepositoryImpl implements ReservationDetailCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public ReservationDetailCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QReservation reservation = QReservation.reservation;
    QAccommodation accommodation = QAccommodation.accommodation;
    QPost post = QPost.post;
    QAccommodationImage accommodationImage = QAccommodationImage.accommodationImage;
    QPayment payment = QPayment.payment;

    @Override
    public List<GuestReservationResponse> getGuestReservations(Long userId) {
        return jpaQueryFactory
                .select(Projections.fields(GuestReservationResponse.class,
                        reservation.accommodationId,
                        reservation.checkInDate,
                        reservation.checkOutDate,
                        reservation.status,
                        reservation.totalPrice,
                        post.title,
                        accommodation.lotAddress,
                        accommodation.detailAddress,
                        accommodation.floor,
                        accommodationImage.imageUrl,
                        payment.impUid
                ))
                .from(reservation)
                .join(accommodation).on(accommodation.id.eq(reservation.accommodationId))
                .join(post).on(post.id.eq(accommodation.postId))
                .join(accommodationImage).on(accommodationImage.accommodationId.eq(accommodation.id))
                .join(payment).on(reservation.id.eq(payment.reservationId))
                .where(reservation.userId.eq(userId)
                        .and(accommodationImage.imageOrder.eq(0)))
                .orderBy(payment.createdAt.desc()) // 최신 결제 기준으로 정렬
                .fetch();
    }

    //    public List<AccommodationDTO> findAccommodationsByUserId(Long userId) {
//        QReservation reservation = QReservation.reservation;
//        QAccommodation accommodation = QAccommodation.accommodation;
//        QPost post = QPost.post;
//
//        return queryFactory.select(new QAccommodationDTO(reservation.accommodationId, post.title))
//                .from(reservation)
//                .join(reservation.accommodation, accommodation)
//                .join(accommodation.post, post)
//                .where(post.user.id.eq(userId))
//                .fetch();
//    }


}
