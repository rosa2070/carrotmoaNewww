package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.BestAccommodationResponse;
import carrotmoa.carrotmoa.repository.BestAccommodationCustomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BestAccommodationService {

    private final RedisTemplate<String, Object> bestAccommodationRedisTemplate;
    private final BestAccommodationCustomRepository bestAccommodationCustomRepository;
    private final ObjectMapper objectMapper;

    public BestAccommodationService(RedisTemplate<String, Object> bestAccommodationRedisTemplate,
                                    BestAccommodationCustomRepository bestAccommodationCustomRepository,
                                    ObjectMapper objectMapper) {
        this.bestAccommodationRedisTemplate = bestAccommodationRedisTemplate;
        this.bestAccommodationCustomRepository = bestAccommodationCustomRepository;
        this.objectMapper = objectMapper;
    }

    //    @Cacheable(cacheNames = "getBestAccommodations", key = "'bestAccommodations'", cacheManager = "bestAccommodationCacheManager")
    public List<BestAccommodationResponse> getBestAccommodations() {
        return bestAccommodationCustomRepository.getBestAccommodations();
    }

    // Redis에서 인기 숙소를 가져오는 메서드
    public List<BestAccommodationResponse> getBestAccommodationsFromRedis() {
        String sortedSetKey = "best_accommodations";

        // ZSetOperations 객체 생성
        ZSetOperations<String, Object> zSetOperations = bestAccommodationRedisTemplate.opsForZSet();

        // Redis에서 상위 8개 인기 숙소의 ID를 가져옵니다.
        Set<Object> top8Accommodations = zSetOperations.reverseRange(sortedSetKey, 0, 7);

        // Redis에 데이터가 없으면, RDS에서 데이터를 조회하고 Redis에 저장
        if (top8Accommodations == null || top8Accommodations.isEmpty()) {
            // RDS에서 인기 숙소 데이터를 가져옵니다.
            List<BestAccommodationResponse> bestAccommodations = bestAccommodationCustomRepository.getBestAccommodations();

            // RDS에서 가져온 데이터를 Redis에 저장
            saveBestAccommodationsToRedis(bestAccommodations);

            // Redis에서 데이터를 가져오기 위해 다시 조회
            // (Redis에 데이터가 저장된 후 바로 Redis에서 가져오기)
            top8Accommodations = zSetOperations.reverseRange(sortedSetKey, 0, 7);
        }

        // Redis에서 인기 숙소 데이터를 가져옵니다.
        return getAccommodationsFromRedis(top8Accommodations);
    }

    // RDS에서 가져온 데이터를 Redis에 저장하는 메서드
    public void saveBestAccommodationsToRedis(List<BestAccommodationResponse> bestAccommodations) {
        String sortedSetKey = "best_accommodations"; // Redis의 key
        ZSetOperations<String, Object> zSetOperations = bestAccommodationRedisTemplate.opsForZSet(); // ZSetOperations 객체 생성

        for (BestAccommodationResponse accommodation : bestAccommodations) {
            try {
                // 인기 숙소의 예약 수를 점수로 사용
                double score = (double) accommodation.getReservationCount(); // 예약 수를 점수로 사용

                // 숙소의 모든 정보를 JSON 문자열로 변환
                String accommodationJson = objectMapper.writeValueAsString(accommodation);

                // Redis Sorted Set에 숙소의 ID(직접 Long 타입 사용)와 예약 수를 저장
                zSetOperations.add(sortedSetKey, accommodation.getId(), score);

                // 숙소 정보는 Redis Hash에 저장 (숙소 ID를 Long으로 사용)
                bestAccommodationRedisTemplate.opsForHash().put("accommodation:" + accommodation.getId(), "info", accommodationJson);
            } catch (JsonProcessingException e) {
                // JSON 변환 실패 시 예외 처리
                e.printStackTrace();
            }
        }
    }

    // Redis에서 인기 숙소 데이터를 가져오는 메서드
    private List<BestAccommodationResponse> getAccommodationsFromRedis(Set<Object> accommodationIds) {
        List<BestAccommodationResponse> accommodations = new ArrayList<>();
        for (Object id : accommodationIds) {
            // Redis Hash에서 숙소의 상세 정보를 가져오기
            String accommodationJson = (String) bestAccommodationRedisTemplate.opsForHash().get("accommodation:" + id, "info");

            if (accommodationJson != null) {
                try {
                    // JSON 문자열을 BestAccommodationResponse 객체로 변환
                    BestAccommodationResponse accommodation = objectMapper.readValue(accommodationJson, BestAccommodationResponse.class);
                    accommodations.add(accommodation);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return accommodations;
    }

}
