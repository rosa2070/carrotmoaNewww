package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.request.SaveFleaMarketPostRequest;
import carrotmoa.carrotmoa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FleaMarketService {

    @Autowired
    PostRepository postRepository;

    @Transactional
    public Long savePost(SaveFleaMarketPostRequest saveFleaMarketPostRequest) {

        return 1L;
    }
}
