package carrotmoa.carrotmoa.util;

import carrotmoa.carrotmoa.model.request.RequestDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* carrotmoa.carrotmoa.controller.api.*.*(..)) && args(requestDTO,..)")
    public void logBefore(JoinPoint joinPoint, RequestDTO requestDTO) {
        logMethodExecution(joinPoint);
        logRequestDTO(requestDTO);
    }

    private void logMethodExecution(JoinPoint joinPoint) {
        log.info("메서드 실행 전 - {}", joinPoint.getSignature().getName());
    }

    private void logRequestDTO(RequestDTO requestDTO) {
        log.info("Request DTO 클래스: {}", requestDTO.getClass().getSimpleName());
        log.info("파라미터 값: {}", Arrays.toString(requestDTO.getClass().getDeclaredFields()));

        Arrays.stream(requestDTO.getClass().getDeclaredFields())
                .forEach(field -> logField(requestDTO, field));
    }

    private void logField(RequestDTO requestDTO, Field field) {
        field.setAccessible(true);
        try {
            Object value = field.get(requestDTO);
            if (value instanceof MultipartFile) {
                logMultipartFileField((MultipartFile) value);
            } else if (value instanceof List) {
                logListField(field, requestDTO);
            } else {
                log.info("{} ({}): {}", field.getName(), field.getType().getSimpleName(), value);
            }
        } catch (IllegalAccessException e) {
            log.warn("필드 값을 읽는 중 오류 발생: {}", field.getName());
        }
    }

    private void logMultipartFileField(MultipartFile file) {
        if (file != null) {
            String anonymizedFileName = "file_" + System.currentTimeMillis() + ".jpg"; // 파일명 익명화
            log.info("Uploaded File - Name: {}, Size: {}, ContentType: {}",
                    anonymizedFileName, file.getSize(), file.getContentType());
        }
    }

    private void logListField(Field field, RequestDTO requestDTO) {
        try {
            List<?> list = (List<?>) field.get(requestDTO);
            if (list != null && !list.isEmpty()) {
                log.info("{} (List):", field.getName());
                for (Object item : list) {
                    logListItem(item);
                }
            }
        } catch (IllegalAccessException e) {
            log.warn("리스트 필드 값을 읽는 중 오류 발생: {}", field.getName());
        }
    }

    private void logListItem(Object item) {
        if (item instanceof MultipartFile) {
            logMultipartFileField((MultipartFile) item);
        } else if (item instanceof RequestDTO) {
            logFieldDetails(item);
        } else {
            log.info("List Item: {}", item);
        }
    }

    private void logFieldDetails(Object obj) {
        Arrays.stream(obj.getClass().getDeclaredFields())
                .forEach(field -> logFieldDetail(obj, field));
    }

    private void logFieldDetail(Object obj, Field field) {
        field.setAccessible(true);
        try {
            log.info("{}: {}", field.getName(), field.get(obj));
        } catch (IllegalAccessException e) {
            log.warn("필드 값을 읽는 중 오류 발생: {}", field.getName());
        }
    }
}
