package com.example.study.ratelimit.bucket.filter;

import com.example.study.ratelimit.bucket.RateLimitService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
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

        String ip = request.getRemoteAddr();
        String api = request.getRequestURI();

        if (api.contains("/rate-limit/bucket")) {
            Bucket bucket = rateLimitService.resolveBucket(ip, api);
            if (bucket.tryConsume(1)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(429);
                response.getWriter().write("Rate limit exceeded for IP: " + ip + ", API: " + api);
            }
        } else {
            // bucket API가 아니면 그냥 통과
            filterChain.doFilter(request, response);
        }
    }
}
