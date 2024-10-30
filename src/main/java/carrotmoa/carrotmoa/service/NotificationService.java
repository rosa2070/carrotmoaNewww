package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.NotificationResponse;
import carrotmoa.carrotmoa.repository.EmitterRepository;
import carrotmoa.carrotmoa.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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


    // 여기에 알림 저장한느 레파ㅣ토리 작성
    public void sendNotification(Long receiverId, String message) {
        SseEmitter emitter = emitterRepository.get(receiverId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (Exception e) {
                emitterRepository.deleteById(receiverId);
            }
        }

        //Notification savedNotification = notificationRepository.save(notification);
    }

    public List<NotificationResponse> findNotificationsByReceiverId(Long receiverId) {
        return notificationRepository.findNotificationsByReceiverId(receiverId);
    }


}




