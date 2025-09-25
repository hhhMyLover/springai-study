package com.wzh.springai.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heath")
public class MainController {

    @GetMapping
    public String heathCheck(){
        return "ok";
    }
}