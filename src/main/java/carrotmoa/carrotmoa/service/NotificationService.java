package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.NotificationResponse;
import carrotmoa.carrotmoa.repository.EmitterRepository;
import carrotmoa.carrotmoa.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

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
        // 타임아웃 시 Emitter 제거
        emitter.onCompletion(() -> emitterRepository.deleteById(userId)); // onCompletion -> 클라이언트 or 서버에서 연결 이 종료시 실행되는 코드
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));
        try {
            // 처음에 SSE 응답을 할 때 아무런 이벤트도 보내지 않으면 재연결 요청을 보낼때나, 아니면 연결 요청 자체에서 오류가 발생합니다.
            //
            //따라서 첫 SSE 응답을 보낼 시에는 반드시 더미 데이터라도 넣어서 데이터를 전달
            emitter.send(SseEmitter.event()
                    .name("connect") // 이벤트 이름 지정
                    .data("connected!")); // 503에러 방지를 위한 더미 데이터
            emitterRepository.save(userId, emitter);
            log.info("SSE 객체가 Map에 잘 저장되었을까요? -> {}",emitterRepository.get(userId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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




