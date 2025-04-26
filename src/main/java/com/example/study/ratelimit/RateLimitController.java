package com.example.study.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/ratelimit")
public class RateLimitController {

    @GetMapping("/api/test")
    public void test() {
        log.info(" > Api CAll Ok");
    }
}
