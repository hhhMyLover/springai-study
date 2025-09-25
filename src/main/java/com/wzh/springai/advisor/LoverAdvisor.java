package com.wzh.springai.advisor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;

import java.util.HashMap;
import java.util.Map;

/**
 * 恋爱助手顾问 - 为聊天添加恋爱相关的上下文和指导
 */
public class LoverAdvisor implements CallAroundAdvisor {

    private static final String ADVISOR_NAME = "lover-advisor";
    
    @Override
    public String getName() {
        return ADVISOR_NAME;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        // 在请求前添加恋爱助手的上下文
        String originalUserText = advisedRequest.userText();
        
        // 增强用户消息，添加恋爱助手的上下文
        String enhancedMessage = enhanceMessageForLover(originalUserText);
        
        // 创建新的请求，包含增强的消息
        AdvisedRequest newRequest = AdvisedRequest.builder()
                .userText(enhancedMessage)
                .chatOptions(advisedRequest.chatOptions())
                .systemText(advisedRequest.systemText())
                .systemParams(advisedRequest.systemParams())
                .userParams(advisedRequest.userParams())
                .build();

        // 继续执行链
        AdvisedResponse response = chain.nextAroundCall(newRequest);
        
        // 可以在这里对响应进行后处理
        return response;
    }

    private String enhanceMessageForLover(String originalMessage) {
        // 简单的消息增强逻辑
        if (originalMessage.toLowerCase().contains("分手") || 
            originalMessage.toLowerCase().contains("吵架") ||
            originalMessage.toLowerCase().contains("问题")) {
            return originalMessage + "\n\n请以温和、理解的方式给出建议，重点关注情感沟通和解决方案。";
        }
        return originalMessage;
    }
}