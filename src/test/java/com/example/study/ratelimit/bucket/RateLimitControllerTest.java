package com.example.study.ratelimit.bucket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("> rate-limit - bucket RateLimitController 실 API 테스트")
class RateLimitControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("실제 API 호출로 레이트리밋 테스트")
    void realRateLimitTest() throws Exception {
        // 10개까지는 OK
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(get("/rate-limit/bucket/api/test"))
                    .andExpect(status().isOk());
        }

        // 11번째 요청은 429 (Too Many Requests)
        mockMvc.perform(get("/rate-limit/bucket/api/test"))
                .andExpect(status().isTooManyRequests());
    }
}