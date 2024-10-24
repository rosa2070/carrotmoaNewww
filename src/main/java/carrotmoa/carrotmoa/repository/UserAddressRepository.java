package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
