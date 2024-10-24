package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository  extends JpaRepository<PostImage, Long> {
}
