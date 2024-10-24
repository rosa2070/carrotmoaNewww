package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.SaveCommunityCommentResponse;
import carrotmoa.carrotmoa.service.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityCommentController {
    final private CommunityCommentService communityCommentService;

    @PostMapping("/posts/{communityPostId}/comments")
    public ResponseEntity<SaveCommunityCommentResponse> createCommunityComment(@PathVariable("communityPostId") Long communityPostId,
                                                                               @RequestBody SaveCommunityCommentResponse saveCommunityCommentRequest) {
        SaveCommunityCommentResponse communityComment = communityCommentService.createCommunityComment(communityPostId, saveCommunityCommentRequest);
        return new ResponseEntity<>(communityComment, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{communityPostId}/comments")
    public ResponseEntity<Map<String, Object>> findActiveCommentsByCommunityPostId(@PathVariable("communityPostId") Long communityPostId) {
        Map<String, Object> activeComments = communityCommentService.findActiveCommentsByCommunityPostId(communityPostId);
        return new ResponseEntity<>(activeComments, HttpStatus.OK);
    }

}
