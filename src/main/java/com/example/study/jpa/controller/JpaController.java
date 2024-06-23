package com.example.study.jpa.controller;

import com.example.study.jpa.service.JpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class JpaController {

    private final JpaService jpaService;

    @PostMapping("/bulkInsert")
    public void 벌크인서트_테스트() {
        jpaService.벌크인서트_테스트();

    }
}
