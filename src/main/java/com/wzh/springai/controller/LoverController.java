package com.wzh.springai.controller;

import com.wzh.springai.service.LoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lover")
public class LoverController {

    @Autowired
    private LoverService loverService;

    @GetMapping("/chatLover")
    public String loverChat(String message) {
        return loverService.chat(message);
    }

    @GetMapping("/chatMemory")
    public String loverChatMemory(String message ,String chatId){
        return loverService.loverChatMemory(message,chatId);
    }
}
