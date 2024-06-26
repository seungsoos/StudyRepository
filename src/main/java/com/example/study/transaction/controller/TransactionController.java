package com.example.study.transaction.controller;

import com.example.study.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/author")
    public void author(@RequestParam Long num) {
        transactionService.author(num);
    }

    @GetMapping("/book")
    public void book(@RequestParam Long num) {
       transactionService.book(num);
    }

    @PostMapping("/save")
    public void save() {
        transactionService.save();
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long num) {
        transactionService.delete(num);
    }

    @PostMapping("/transaction1")
    public void 하나의_트랜잭션에서_예외발생_Case() {
        transactionService.하나의_트랜잭션에서_예외발생_Case();
    }

    @PostMapping("/transaction2")
    public void 정상Case_같은_서비스에서_트랜잭션_전파() {
        transactionService.정상Case_같은_서비스에서_트랜잭션_전파();
    }

    @PostMapping("/transaction3")
    public void 같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case() {
        transactionService.같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case();
    }

    @PostMapping("/transaction4")
    public void 같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case() {
        transactionService.같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case();
    }

    @PostMapping("/transaction5")
    public void 다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case() {
        transactionService.다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case();
    }

    @PostMapping("/transaction6")
    public void 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case() {
        transactionService.트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case();
    }


    @PostMapping("/transaction7")
    public void 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_예외발생_Case() {
        transactionService.트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_예외발생_Case();
    }


    @PostMapping("/transaction8")
    public void 트랜잭션_전파_테스트_1(){
        transactionService.트랜잭션_전파_테스트_1();
    }

}
