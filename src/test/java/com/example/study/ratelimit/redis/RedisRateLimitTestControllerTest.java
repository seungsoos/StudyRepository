package com.example.study.ratelimit.redis;

import com.example.container.TestContainerConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(TestContainerConfig.class)
@ActiveProfiles("test")
@DisplayName("> redis AOP 기반 rate-limit 통합테스트")
class RedisRateLimitTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @AfterEach
    void tearDown() {
        try {
            Objects.requireNonNull(redisTemplate.getConnectionFactory())
                    .getConnection()
                    .serverCommands()
                    .flushAll();
        } catch (Exception e) {
            // 로그 추가
            System.err.println("Redis 정리 중 오류 발생: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("AOP 기반 Redis rate-limit - 5회까지만 허용, 초과 시 429")
    void testAopRateLimit() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/rate-limit/redis/api/test"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        // 6번째는 429 에러
        mockMvc.perform(get("/rate-limit/redis/api/test"))
                .andExpect(status().isTooManyRequests())
                .andDo(print());
    }

    @Test
    @DisplayName("AOP 미적용 AIP 테스트")
    void testAopRateLimit2() throws Exception {
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(get("/rate-limit/redis/api/test2"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

    }




}