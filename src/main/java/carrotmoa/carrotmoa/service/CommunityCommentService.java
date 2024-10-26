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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
        if (!communityPostRepository.existsById(communityPostId)) {
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

        // parentId가 null인 최상위 댓글들을 저장할 리스트
        List<CommunityCommentResponse> topLevelComments = new ArrayList<>();
        Map<Long, List<CommunityCommentResponse>> repliesMap = new HashMap<>();

        for (CommunityCommentResponse comment : commentList) {
            if (comment.getParentId() == null) {
                // 최상위 댓글은 따로 저장
                topLevelComments.add(comment);
            } else {
                // 그 외의 대댓글들은 repliesMap에 저장
                repliesMap.computeIfAbsent(comment.getParentId(), k -> new ArrayList<>()).add(comment);
            }
        }

        // 최상위 댓글들로부터 시작해서 재귀적으로 하위 댓글을 설정
        List<CommunityCommentResponse> structuredCommentList = new ArrayList<>();
        for (CommunityCommentResponse topComment : topLevelComments) {
            structuredCommentList.add(topComment);
            // 해당 최상위 댓글의 하위 대댓글들을 재귀적으로 추가
            List<CommunityCommentResponse> replies = buildCommentHierarchy(topComment.getId(), repliesMap);
            structuredCommentList.addAll(replies);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("commentCount", commentCount);
        responseMap.put("commentList", structuredCommentList);
        return responseMap;
    }

    @Transactional
    public Long softDeleteCommentById(Long commentId, Long communityPostId) {
        CommunityComment communityComment = communityCommentRepository
                .findByIdAndCommunityPostId(commentId, communityPostId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
        communityComment.softDeleteComment(true);
        return communityComment.getId();
    }


    // 재귀적으로 댓글 구조를 설정하는 함수
    private List<CommunityCommentResponse> buildCommentHierarchy(Long parentId, Map<Long, List<CommunityCommentResponse>> repliesMap) {
        List<CommunityCommentResponse> structuredComments = new ArrayList<>();

        // parentId에 해당하는 댓글 리스트를 가져옴
        List<CommunityCommentResponse> comments = repliesMap.get(parentId);
        if (comments != null) {
            for (CommunityCommentResponse comment : comments) {
                structuredComments.add(comment);
                // 현재 댓글의 하위 댓글들도 추가 (재귀 호출)
                List<CommunityCommentResponse> replies = buildCommentHierarchy(comment.getId(), repliesMap);
                structuredComments.addAll(replies);
            }
        }
        return structuredComments;
    }
}
