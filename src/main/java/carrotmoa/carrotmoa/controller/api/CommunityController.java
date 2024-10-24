package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.SaveCommunityPostRequest;
import carrotmoa.carrotmoa.model.response.CommunityCategoryResponse;
import carrotmoa.carrotmoa.model.response.CommunityPostDetailResponse;
import carrotmoa.carrotmoa.model.response.CommunityPostListResponse;
import carrotmoa.carrotmoa.service.CommunityCategoryService;
import carrotmoa.carrotmoa.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {
    final private CommunityPostService communityPostService;
    final private CommunityCategoryService categoriesService;

    @GetMapping("/posts")
    public ResponseEntity<List<CommunityPostListResponse>> getAllCommunityPosts() {
        List<CommunityPostListResponse> allCommunityPosts = communityPostService.getAllCommunityPosts();
        return new ResponseEntity<>(allCommunityPosts, HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> createCommunityPost(@RequestBody SaveCommunityPostRequest saveCommunityPostRequest) {
        log.info("게시글 삽입 데이터 확인: {}", saveCommunityPostRequest);
        Long postId = communityPostService.createCommunityPost(saveCommunityPostRequest);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }


    @GetMapping("/sub-categories")
    public ResponseEntity<List<CommunityCategoryResponse>> getSubCategories() {
        List<CommunityCategoryResponse> Categories = categoriesService.getSubCategories();
        return new ResponseEntity<>(Categories, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CommunityCategoryResponse>> getAllCategories() {
        List<CommunityCategoryResponse> categories = categoriesService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/posts/{communityPostId}")
    public ResponseEntity<CommunityPostDetailResponse> findCommunityPostByPostId(@PathVariable("communityPostId") Long id) {
        CommunityPostDetailResponse communityPostByPostId = communityPostService.findCommunityPostDetail(id);
        return new ResponseEntity<>(communityPostByPostId, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{communityPostId}")
    public ResponseEntity<Integer> deleteByCommunityPostId(@PathVariable("communityPostId")Long communityPostId) {
        int deletedCount = communityPostService.deleteByCommunityPostId(communityPostId);
        return new ResponseEntity<>(deletedCount, HttpStatus.OK);
    }

}
