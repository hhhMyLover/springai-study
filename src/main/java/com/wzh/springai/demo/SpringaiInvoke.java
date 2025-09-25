package com.wzh.springai.demo;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Spring AI demo - 用于测试Spring AI基本功能
 * 可以通过注释@Component来禁用自动运行
 */
// @Component  // 取消注释以启用自动运行
public class SpringaiInvoke implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringaiInvoke.class);
    
    @Resource
    private ChatModel chatModel;

    @Override
    public void run(String... args) throws Exception {
        try {
            AssistantMessage message = chatModel.call(new Prompt("你是谁"))
                    .getResult()
                    .getOutput();
            log.info("springai alibaba 调用qwen message:[{}]", message.getText());
            System.out.println("Spring AI 响应: " + message.getText());
        } catch (Exception e) {
            log.error("Spring AI 调用失败: ", e);
        }
    }
}