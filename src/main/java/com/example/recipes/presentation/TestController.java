package com.example.recipes.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class TestController {

    @PostMapping("/shutdown")
    public String shutdown() {
        return "/shutdown is accessed";
    }
}
