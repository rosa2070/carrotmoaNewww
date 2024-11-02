package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.*;
import carrotmoa.carrotmoa.model.response.PaymentDetailResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PaymentDetailCustomRepositoryImpl implements PaymentDetailCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PaymentDetailCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PaymentDetailResponse> getSettlementList(String title, LocalDate startDate, LocalDate endDate) {
        QPayment payment = QPayment.payment;
        QReservation reservation = QReservation.reservation;
        QUser user = QUser.user;
        QAccommodation accommodation = QAccommodation.accommodation;
        QPost post = QPost.post;

        List<PaymentDetailResponse> results = jpaQueryFactory
                .select(Projections.fields(PaymentDetailResponse.class,
                        reservation.checkInDate.as("settlementDate"), // 정산 일자
                        post.title,
                        user.name,
                        reservation.checkInDate,
                        payment.paymentAmount
                ))
                .from(payment)
                .join(reservation).on(payment.reservationId.eq(reservation.id))
                .join(user).on(reservation.userId.eq(user.id))
                .join(accommodation).on(reservation.accommodationId.eq(accommodation.id))
                .join(post).on(accommodation.postId.eq(post.id))
                .where(post.title.eq(title)
                        .and(reservation.checkInDate.between(startDate, endDate))
                        .and(post.isDeleted.eq(false))
                        .and(payment.status.eq("paid")))
                .fetch();

        // 결과에 1일 추가
        results.forEach(result -> result.setSettlementDate(result.getSettlementDate().plusDays(1)));

        return results;

    }


}
