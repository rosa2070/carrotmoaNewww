package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.QAccommodation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AccommodationDetailCustomRepositoryImpl implements AccommodationDetailRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AccommodationDetailCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Accommodation getAccommodationById(Long id) {
        QAccommodation a = QAccommodation.accommodation;

        return jpaQueryFactory.selectFrom(a).where(a.id.eq(id)).fetchOne(); // 단일 결과를 가져옴

    }
}
