package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.model.response.BestAccommodationResponse;
import carrotmoa.carrotmoa.repository.BestAccommodationCustomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    // RDS에서 이미 정렬된 인기 숙소 8개를 가져오는 메서드
    public List<BestAccommodationResponse> getBestAccommodationsFromRds() {
        log.info("Fetching best accommodations from RDS.");
        return bestAccommodationCustomRepository.getBestAccommodations();
    }

    // Redis에서 인기 숙소 8개를 가져오는 메서드
    public List<BestAccommodationResponse> getBestAccommodationsFromRedis() {
        String redisKey = "best_accommodations";
        log.info("Fetching best accommodations from Redis list: {}", redisKey);

        // Redis에서 데이터가 없으면 RDS에서 가져와서 Redis에 저장
        List<Object> redisData = bestAccommodationRedisTemplate.opsForList().range(redisKey, 0, -1);
        if (redisData == null || redisData.isEmpty()) {
            log.info("No data found in Redis. Fetching from RDS.");
            // RDS에서 인기 숙소 데이터를 가져옵니다.
            List<BestAccommodationResponse> bestAccommodations = getBestAccommodationsFromRds();

            // RDS에서 가져온 데이터를 Redis에 저장
            saveBestAccommodationsToRedis(bestAccommodations);

            // Redis에서 데이터를 가져오기 위해 다시 조회
            redisData = bestAccommodationRedisTemplate.opsForList().range(redisKey, 0, -1);
        }

        log.info("Fetched {} accommodations from Redis.", redisData.size());
        return getAccommodationsFromRedis(redisData);
    }

    // RDS에서 가져온 데이터를 Redis에 저장하는 메서드
    public void saveBestAccommodationsToRedis(List<BestAccommodationResponse> bestAccommodations) {
        String redisKey = "best_accommodations"; // Redis의 key
        // Redis List에 데이터를 저장
        for (BestAccommodationResponse accommodation : bestAccommodations) {
            try {
                // 숙소의 모든 정보를 JSON 문자열로 변환
                String accommodationJson = objectMapper.writeValueAsString(accommodation);

                // Redis List에 순차적으로 저장
                bestAccommodationRedisTemplate.opsForList().rightPush(redisKey, accommodationJson);

                log.info("Saved accommodation with ID {} to Redis.", accommodation.getId());
            } catch (JsonProcessingException e) {
                log.error("Failed to convert accommodation with ID {} to JSON.", accommodation.getId(), e);
            }
        }
    }

    // Redis에서 숙소 정보를 가져오는 메서드
    private List<BestAccommodationResponse> getAccommodationsFromRedis(List<Object> redisData) {
        List<BestAccommodationResponse> accommodations = new ArrayList<>();
        log.info("Fetching accommodation details from Redis.");

        for (Object accommodationJson : redisData) {
            try {
                // JSON 문자열을 BestAccommodationResponse 객체로 변환
                BestAccommodationResponse accommodation = objectMapper.readValue((String) accommodationJson, BestAccommodationResponse.class);
                accommodations.add(accommodation);
                log.info("Fetched accommodation from Redis.");
            } catch (JsonProcessingException e) {
                log.error("Failed to parse accommodation JSON.", e);
            }
        }

        log.info("Total {} accommodations fetched from Redis.", accommodations.size());
        return accommodations;
    }
}