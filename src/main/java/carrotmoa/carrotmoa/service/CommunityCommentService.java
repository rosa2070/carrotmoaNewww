package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.CommunityComment;
import carrotmoa.carrotmoa.exception.ResourceNotFoundException;
import carrotmoa.carrotmoa.model.request.SaveCommunityCommentRequest;
import carrotmoa.carrotmoa.model.response.SaveCommunityCommentResponse;
import carrotmoa.carrotmoa.model.response.CommunityCommentResponse;
import carrotmoa.carrotmoa.repository.CommunityCommentRepository;
import carrotmoa.carrotmoa.repository.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityCommentService {
    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityPostRepository communityPostRepository;

    @Transactional
    public Long createCommunityComment(Long communityPostId, SaveCommunityCommentRequest request) {
        if(!communityPostRepository.existsById(communityPostId)) {
            throw new ResourceNotFoundException("해당 게시글이 존재하지 않습니다.");
        }
        request.setCommunityPostId(communityPostId);
        CommunityComment commentEntity = communityCommentRepository.save(request.toCommunityCommentEntity());
            return commentEntity.getId();
    }


    @Transactional(readOnly = true)
    public Map<String, Object> findActiveCommentsByCommunityPostId(Long communityPostId) {
        List<CommunityCommentResponse> commentList = communityCommentRepository.findActiveCommentsByCommunityPostId(communityPostId);
        int commentCount = communityCommentRepository.countByCommunityPostId(communityPostId);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("commentCount", commentCount);
        responseMap.put("commentList", commentList);
        return responseMap;
    }
}
