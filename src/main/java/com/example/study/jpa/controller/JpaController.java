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

    @PostMapping("/findAll")
    public void findAll() {
        jpaService.findAll();
    }

    @PostMapping("/bulkInsert")
    public void 벌크인서트_테스트() {
        jpaService.벌크인서트_테스트();
    }

    @PostMapping("/save_test")
    public void save_1000건_시간측정() {
        jpaService.save_1000건_시간측정();
    }

    @PostMapping("/saveAll")
    public void saveAll_1000건_시간측정() {
        jpaService.saveAll_1000건_시간측정();
    }

    @PostMapping("/save_not_select")
    public void save시에_select_쿼리가_나가지않게() {
        jpaService.save시에_select_쿼리가_나가지않게();
    }

    @PostMapping("/test1")
    public void String_ID_save쿼리가_날아가는시점() {
        jpaService.String_ID_save쿼리가_날아가는시점();
    }

    @PostMapping("/test2")
    public void String_ID_save_후_로그찍어기() {
        jpaService.String_ID_save_후_로그찍어기();
    }

    @PostMapping("/test3")
    public void 동일한_엔티티를_조건이_다르게_조회() {
        jpaService.동일한_엔티티를_조건이_다르게_조회();
    }


}
