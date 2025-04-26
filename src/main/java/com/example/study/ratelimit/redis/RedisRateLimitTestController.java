package com.example.study.ratelimit.redis;

import com.example.study.ratelimit.redis.annotation.RateLimit;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/rate-limit/redis")
public class RedisRateLimitTestController {

    @RateLimit(limit = 5)
    @GetMapping("/api/test")
    public void test() {
      log.info(" > Api CAll Ok");
    }

    @GetMapping("/api/test2")
    public void test2() {
        log.info(" > AOP 미적용 Api CAll Ok");
    }
}
