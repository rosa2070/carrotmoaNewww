package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
