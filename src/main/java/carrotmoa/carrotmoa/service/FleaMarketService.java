package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.request.SaveFleaMarketPostRequest;
import carrotmoa.carrotmoa.repository.PostRepository;
import carrotmoa.carrotmoa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FleaMarketService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Long savePost(SaveFleaMarketPostRequest saveFleaMarketPostRequest) {
        validatePost(saveFleaMarketPostRequest);

        Post post = postRepository.save(saveFleaMarketPostRequest.toPostEntity());
        productRepository.save(saveFleaMarketPostRequest.toProductEntity(post.getId()));
        return post.getId();
    }

    private void validatePost(SaveFleaMarketPostRequest saveFleaMarketPostRequest) {
        //TODO : 검증 추가
    }
}
