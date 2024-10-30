package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.NotificationResponse;
import carrotmoa.carrotmoa.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    // 1. SSE 연결하는 API
    @GetMapping(value = "/sse/notifications/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@PathVariable("userId") Long userId) {
        System.out.println(userId);
        return ResponseEntity.ok(notificationService.subscribe(userId));
    }

    // 2. DB에서 수신자 아이디로 알림 리스트 가져오는 API

    @GetMapping("/api/notifications/{receiverId}")
    public ResponseEntity<List<NotificationResponse>> findNotificationsByReceiverId(@PathVariable("receiverId") Long receiverId) {
        return ResponseEntity.ok(notificationService.findNotificationsByReceiverId(receiverId));
    }




}