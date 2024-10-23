package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.HostAdditionalForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostAdditionalFormRepository extends JpaRepository<HostAdditionalForm, Long> {
    public HostAdditionalForm findByUserId(Long UserId);
}
