package com.example.study.ratelimit.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisRateLimitService {

    private final RedisTemplate redisTemplate;

    public boolean isAllowed(String key, int limit, Duration duration) {
        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount == 1) {
            redisTemplate.expire(key, duration);
        }

        return currentCount <= limit;
    }
}
