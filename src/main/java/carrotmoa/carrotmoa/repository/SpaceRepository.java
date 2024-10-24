package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Space;
import carrotmoa.carrotmoa.model.response.SpaceImageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public interface SpaceRepository extends JpaRepository<Space, Long> {

}
