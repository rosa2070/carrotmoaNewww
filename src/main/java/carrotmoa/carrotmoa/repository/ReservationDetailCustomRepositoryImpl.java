package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.*;
import carrotmoa.carrotmoa.model.response.GuestReservationResponse;
import carrotmoa.carrotmoa.model.response.HostReservationResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.catalina.Host;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDetailCustomRepositoryImpl implements ReservationDetailCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ReservationDetailCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QReservation reservation = QReservation.reservation;
    QAccommodation accommodation = QAccommodation.accommodation;
    QPost post = QPost.post;
    QAccommodationImage accommodationImage = QAccommodationImage.accommodationImage;
    QPayment payment = QPayment.payment;
    QUser user = QUser.user;

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
                        payment.impUid,
                        reservation.id.as("reservationId")
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

    @Override
    public List<HostReservationResponse> getHostReservations(Long hostId) {
        return jpaQueryFactory
                .select(Projections.fields(HostReservationResponse.class,
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
                        user.name.as("guestName")
                ))
                .from(reservation)
                .join(accommodation).on(accommodation.id.eq(reservation.accommodationId))
                .join(post).on(post.id.eq(accommodation.postId))
                .join(accommodationImage).on(accommodationImage.accommodationId.eq(accommodation.id))
                .join(user).on(reservation.userId.eq(user.id))
                .where(accommodationImage.imageOrder.eq(0)
                        .and(post.userId.eq(hostId)))
                .orderBy(reservation.createdAt.desc())
                .fetch();
    }


}
