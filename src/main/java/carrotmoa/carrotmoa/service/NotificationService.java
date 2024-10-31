package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Notification;
import carrotmoa.carrotmoa.enums.NotificationType;
import carrotmoa.carrotmoa.model.request.SaveNotificationRequest;
import carrotmoa.carrotmoa.model.response.NotificationResponse;
import carrotmoa.carrotmoa.model.response.SseNotificationResponse;
import carrotmoa.carrotmoa.repository.EmitterRepository;
import carrotmoa.carrotmoa.repository.NotificationRepository;
import carrotmoa.carrotmoa.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60; // 연결 시간 10분

    // 계속 생성안되게 검증 로직 추가하기.
    public SseEmitter subscribe(Long userId) {
//        if (emitterRepository.existsById(userId)) {
//            return emitterRepository.getById(userId); // 기존 Emitter 반환
//        }
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));
        try {
            emitter.send(SseEmitter.event()
                    .name("connect") // 이벤트 이름 지정
                    .data("connected!")); // 503에러 방지를 위한 더미 데이터
            emitterRepository.save(userId, emitter);
            log.info("SSE 객체가 Map에 잘 저장되었을까요? -> {}", emitterRepository.get(userId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return emitter;
    }


    // 여기서는 SSE 실시간 알림 발송(1) + 알림 테이블에 DB 적재(2)를 한 번에 하는 메서드
    @Transactional
    public void sendNotification(Long receiverId, SaveNotificationRequest saveNotificationRequest, String senderUserNickname, String picUrl) {
        Notification notification = saveNotification(saveNotificationRequest);

        SseNotificationResponse sseNotificationResponse = new SseNotificationResponse(NotificationType.COMMENT.getTitle(), senderUserNickname, picUrl,saveNotificationRequest.getMessage(), saveNotificationRequest.getUrl(), notification.isRead(), notification.isDeleted(),DateTimeUtil.formatElapsedTime(notification.getCreatedAt()));
        sendSseNotification(receiverId, sseNotificationResponse);
    }

    // 1. 알림 저장 메서드
    private Notification saveNotification(SaveNotificationRequest saveNotificationRequest) {
        return notificationRepository.save(saveNotificationRequest.toNotificationEntity());
    }

    // 2. SSE로 알림 전송하는 메서드
    private void sendSseNotification(Long receiverId, SseNotificationResponse sseNotificationResponse) {
        SseEmitter emitter = emitterRepository.get(receiverId);
        if (emitter != null) {
            try {
                // SSE 전송 로직
                emitter.send(SseEmitter.event().data(sseNotificationResponse));
            } catch (Exception e) {
                emitterRepository.deleteById(receiverId);  // SSE 전송 실패 시 Emitter 삭제
            }
        }
    }

    @Transactional(readOnly = true)
    public Slice<NotificationResponse> findNotificationsByReceiverId(Long receiverId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findNotificationsByReceiverId(receiverId, pageable);
    }

}




