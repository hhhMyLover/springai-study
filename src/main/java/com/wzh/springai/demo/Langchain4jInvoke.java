//package com.wzh.springai.demo;
//
//import dev.langchain4j.model.openai.OpenAiChatModel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class Langchain4jInvoke {
//
//    public static void main(String[] args) {
//        log.info("开始调用langchain4j");
//        OpenAiChatModel build = OpenAiChatModel.builder()
//                .apiKey(TestApiKey.API_KEY)
//                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
//                .modelName("qwen-plus")
//                .build();
//        String chat = build.chat("你是谁？");
//        log.info("langchain4j 调用结果：{}",chat);
//    }
//}
