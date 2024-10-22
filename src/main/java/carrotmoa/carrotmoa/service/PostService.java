package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final AccommodationRepository accommodationRepository;

    public PostService(PostRepository postRepository, AccommodationRepository accommodationRepository) {
        this.postRepository = postRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Transactional
    public Post savePost(CreateAccommodationRequest createAccommodationRequest) {
        Post post = createAccommodationRequest.toPostEntity();
        return postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        Post post = postRepository.findById(accommodation.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("포스트를 찾을 수 없습니다."));

        if (updateAccommodationRequest.getTitle() != null) {
            log.info("방 이름 변경: {} -> {}", post.getTitle(), updateAccommodationRequest.getTitle());
            post.setTitle(updateAccommodationRequest.getTitle());
        }

        if (updateAccommodationRequest.getContent() != null) {
            log.info("방 설명 및 이용 방법 변경: {} -> {}", post.getContent(), updateAccommodationRequest.getContent());
            post.setContent(updateAccommodationRequest.getContent());
        }

        postRepository.save(post);
    }


}
