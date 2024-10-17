package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.*;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccommodationDetailCustomRepositoryImpl implements AccommodationDetailCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AccommodationDetailCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public AccommodationDetailResponse getAccommodationDetailById(Long id) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QAccommodationImage accommodationImage = QAccommodationImage.accommodationImage;
        QAccommodationAmenity accommodationAmenity = QAccommodationAmenity.accommodationAmenity;
        QAccommodationSpace accommodationSpace = QAccommodationSpace.accommodationSpace;
        QPost post = QPost.post;

        AccommodationDetailResponse detailResponse = jpaQueryFactory
                .select(Projections.fields(AccommodationDetailResponse.class,
                        accommodation.id,
                        post.title,
                        accommodation.totalArea,
                        accommodation.roadAddress,
                        accommodation.lotAddress,
                        accommodation.detailAddress,
                        accommodation.floor,
                        accommodation.totalFloor,
                        accommodation.price,
                        post.content,
                        accommodation.transportationInfo
                ))
                .from(accommodation)
                .leftJoin(post).on(accommodation.postId.eq(post.id))
                .where(accommodation.id.eq(id))
                .fetchOne();

        // 이미지 URL 쿼리
        List<String> imageUrls = jpaQueryFactory
                .select(accommodationImage.imageUrl)
                .from(accommodationImage)
                .where(accommodationImage.accommodationId.eq(id))
                .fetch();

        List<Long> amenityIds = jpaQueryFactory
                .select(accommodationAmenity.amenityId)
                .from(accommodationAmenity)
                .where(accommodationAmenity.accommodationId.eq(id))
                .fetch();

        List<Integer> spaceCounts = jpaQueryFactory
                .select(accommodationSpace.count)
                .from(accommodationSpace)
                .where(accommodationSpace.accommodationId.eq(id))
                .fetch();


        if (detailResponse != null) {
            detailResponse.setImageUrls(imageUrls);
            detailResponse.setAmenityIds(amenityIds);
            detailResponse.setSpaceCounts(spaceCounts);
        }

        return detailResponse;
    }


}
