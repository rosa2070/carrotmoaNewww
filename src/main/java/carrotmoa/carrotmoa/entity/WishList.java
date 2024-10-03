package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishList extends BaseEntity {

    @Column(name = "user_id")
    private Long userId; // 사용자 ID

    @Column(name = "accommodation_id")
    private Long accommodationId; // 숙소 ID

}
