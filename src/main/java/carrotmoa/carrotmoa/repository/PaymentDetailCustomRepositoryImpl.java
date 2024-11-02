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

        return jpaQueryFactory
                .select(Projections.fields(PaymentDetailResponse.class,
                        reservation.checkInDate.as("settlementDate"),  // 계산 없이 바로 사용
                        post.title,
                        user.name,
                        reservation.checkInDate,
                        payment.paymentAmount
                ))
                .from(payment)
                .join(reservation).on(payment.reservationId.eq(reservation.id)) // 외래 키 없이 조인
                .join(user).on(reservation.userId.eq(user.id)) // 사용자 조인
                .join(accommodation).on(reservation.accommodationId.eq(accommodation.id)) // 숙소 조인
                .join(post).on(accommodation.postId.eq(post.id)) // 게시물 조인
                .where(post.title.eq(title)
                        .and(reservation.checkInDate.between(startDate, endDate))  // 날짜 필터링

                        .and(post.isDeleted.eq(false))
                        .and(payment.status.eq("paid")))
                .fetch(); // 결과를 리스트로 반환
    }


}
