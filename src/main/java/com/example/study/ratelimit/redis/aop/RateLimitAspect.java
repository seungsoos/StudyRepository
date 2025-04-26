package com.example.study.ratelimit.redis.aop;

import com.example.study.ratelimit.redis.annotation.RateLimit;
import com.example.study.ratelimit.redis.exception.RateLimitExceededException;
import com.example.study.ratelimit.redis.RedisRateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.Duration;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RedisRateLimitService rateLimitService;

    @Around("@annotation(com.example.study.ratelimit.redis.annotation.RateLimit)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(" > RateLimitAspect checkRateLimit start");
        // 1. 현재 HttpServletRequest 얻기
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = attributes.getRequest();

        String ip = request.getRemoteAddr();
        String api = request.getRequestURI();

        // 2. 어노테이션 값 가져오기
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        int limit = rateLimit.limit();

        String key = "ratelimit:" + ip + ":" + api;
        log.info(" > key = {}", key);
        log.info(" > limit = {}", limit);

        boolean allowed = rateLimitService.isAllowed(key, limit, Duration.ofMinutes(1));

        if (!allowed) {
            throw new RateLimitExceededException("Rate limit exceeded for IP: " + ip + ", API: " + api);
        }

        return joinPoint.proceed();
    }
}
