package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.CommunityCategory;
import carrotmoa.carrotmoa.model.response.CommunityCategoryResponse;
import carrotmoa.carrotmoa.repository.CommunityCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCategoryService {

    final private CommunityCategoryRepository categoriesRepository;

    public List<CommunityCategoryResponse> getSubCategories() {
        List<CommunityCategory> categoriesEntity = categoriesRepository.findByParentIdIsNotNull();
        return categoriesEntity.stream().map(CommunityCategoryResponse::new)
            .toList();
    }

    public List<CommunityCategoryResponse> getAllCategories() {
        List<CommunityCategory> categoriesEntity = categoriesRepository.findAll();
        return categoriesEntity.stream().map(CommunityCategoryResponse::new)
            .toList();
    }

}
