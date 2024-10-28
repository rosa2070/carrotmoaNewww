package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.repository.EmitterRepository;
import carrotmoa.carrotmoa.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        // 타임아웃 시 Emitter 제거
        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));
        return emitter;
    }

    public void sendNotification(Long receiverId, String message) {
        SseEmitter emitter = emitterRepository.get(receiverId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (Exception e) {
                emitterRepository.deleteById(receiverId);
            }
        }
    }


}




