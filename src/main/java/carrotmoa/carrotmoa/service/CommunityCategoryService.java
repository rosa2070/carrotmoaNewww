package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.CommunityCategory;
import carrotmoa.carrotmoa.model.response.CommunityCategoryResponse;
import carrotmoa.carrotmoa.model.response.CommunityCategoryResponses;
import carrotmoa.carrotmoa.repository.CommunityCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityCategoryService {
    final private CommunityCategoryRepository categoriesRepository;

    @Cacheable(value = "communityCategories", key = "'subCategories'", cacheManager = "communityCacheManager")
    @Transactional(readOnly = true)
    public CommunityCategoryResponses  getSubCategories() {
        List<CommunityCategory> categoriesEntity = categoriesRepository.findByParentIdIsNotNull();
        List<CommunityCategoryResponse> responseList  = categoriesEntity.stream().map(CommunityCategoryResponse::new)
                .toList();
        return new CommunityCategoryResponses(responseList);
    }

    @Transactional(readOnly = true)
    public List<CommunityCategoryResponse> getAllCategories() {
        List<CommunityCategory> categoriesEntity = categoriesRepository.findAll();
        return categoriesEntity.stream().map(CommunityCategoryResponse::new)
            .toList();
    }

}
