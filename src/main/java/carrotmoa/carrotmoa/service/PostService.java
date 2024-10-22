package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(CreateAccommodationRequest createAccommodationRequest) {
        Post post = createAccommodationRequest.toPostEntity();
        return postRepository.save(post);
    }
}
