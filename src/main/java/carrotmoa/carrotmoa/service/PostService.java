package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.request.SaveAccommodationRequest;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostService {
    private static final Long SERVICE_ID = 8L;

    private final PostRepository postRepository;
    private final AccommodationRepository accommodationRepository;

    public PostService(PostRepository postRepository, AccommodationRepository accommodationRepository) {
        this.postRepository = postRepository;
        this.accommodationRepository = accommodationRepository;
    }

    public Post savePost(SaveAccommodationRequest saveAccommodationRequest) {
        Post post = Post.builder()
                .serviceId(SERVICE_ID)
                .userId(saveAccommodationRequest.getUserId())
                .title(saveAccommodationRequest.getTitle())
                .content(saveAccommodationRequest.getContent())
                .build();
        return postRepository.save(post);
    }

    public void updatePost(Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
            .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        Post post = postRepository.findById(accommodation.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("포스트를 찾을 수 없습니다."));

        log.info("방 이름 및 설명 변경 전: 제목: {}, 내용: {}", post.getTitle(), post.getContent());

        post.updatePost(updateAccommodationRequest.getTitle(),
                updateAccommodationRequest.getContent());

        log.info("방 이름 및 설명 변경 후: 제목: {}, 내용: {}", post.getTitle(), post.getContent());

        postRepository.save(post);
    }

    public void markAsDeleted(Long postId) {
        postRepository.markAsDeleted(postId);
    }


}
