package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDetailCustomRepositoryImpl {
    private final JPAQueryFactory jpaQueryFactory;

    public ReservationDetailCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
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
