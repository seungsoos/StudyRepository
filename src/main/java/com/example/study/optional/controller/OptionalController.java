package com.example.study.optional.controller;

import com.example.study.optional.service.OptionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/optional")
public class OptionalController {

    private final OptionalService optionalService;

    @GetMapping("/orElse")
    public void orElse(@RequestParam Long id) {
        optionalService.orElse(id);
    }

    @GetMapping("/orElseGet")
    public void orElseGet(@RequestParam Long id) {
        optionalService.orElseGet(id);
    }

    @GetMapping("/ifPresentOrElse")
    public void ifPresentOrElse(@RequestParam Long id) {
        optionalService.ifPresentOrElse(id);
    }


}
