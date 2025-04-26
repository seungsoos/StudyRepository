package com.example.study.ratelimit.filter;

import com.example.study.ratelimit.RateLimitService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();         // IP 가져오기
        String api = request.getRequestURI();         // 요청한 API 경로 가져오기

        Bucket bucket = rateLimitService.resolveBucket(ip, api);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response); // 요청 통과
        } else {
            response.setStatus(429); // Too Many Requests
            response.getWriter().write("Rate limit exceeded for IP: " + ip + ", API: " + api);
        }
    }
}

