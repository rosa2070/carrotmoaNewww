package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.CommunityComment;
import carrotmoa.carrotmoa.model.response.CommunityCommentResponse;
import carrotmoa.carrotmoa.model.response.SaveCommunityCommentResponse;
import carrotmoa.carrotmoa.repository.CommunityCommentRepository;
import carrotmoa.carrotmoa.repository.CommunityPostRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityCommentService {
    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityPostRepository communityPostRepository;
    private final NotificationService notificationService;

    //    TODO: dto에 userId 변수 정의하고, existsById로 유저 아이디 있는지 검사하는 로직 추가해야함.
    @Transactional
    public SaveCommunityCommentResponse createCommunityComment(Long communityPostId, SaveCommunityCommentResponse dto) {
        if (!communityPostRepository.existsById(communityPostId)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
        dto.setUserId(1L);
        dto.setCommunityPostId(communityPostId);
        CommunityComment CommunityCommentEntity = communityCommentRepository.save(dto.toCommunityCommentEntity());
        int commentCount = communityCommentRepository.countByCommunityPostId(communityPostId);

        // SSE 알림 메서드 사용하기
        notificationService.sendNotification(32L, "테스트 알림입니다.");


        return new SaveCommunityCommentResponse(CommunityCommentEntity, commentCount);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findActiveCommentsByCommunityPostId(Long communityPostId) {
        List<CommunityComment> commentListEntity = communityCommentRepository.findActiveCommentsByCommunityPostId(communityPostId);
        int commentCount = communityCommentRepository.countByCommunityPostId(communityPostId);
        List<CommunityCommentResponse> commentList = commentListEntity.stream().map(CommunityCommentResponse::new).toList();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("commentCount", commentCount);
        responseMap.put("commentList", commentList);
        return responseMap;
    }
}
