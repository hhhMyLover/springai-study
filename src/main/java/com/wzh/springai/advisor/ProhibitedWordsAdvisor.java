package com.wzh.springai.advisor;

import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;

import java.util.Arrays;
import java.util.List;

/**
 * 违禁词过滤顾问 - 检查并过滤不当内容
 */
public class ProhibitedWordsAdvisor implements CallAroundAdvisor {

    private static final String ADVISOR_NAME = "prohibited-words-advisor";
    
    // 简单的违禁词列表
    private final List<String> prohibitedWords = Arrays.asList(
        "暴力", "仇恨", "恶意", "攻击"
    );

    @Override
    public String getName() {
        return ADVISOR_NAME;
    }

    @Override
    public int getOrder() {
        return -1; // 较高优先级，先执行过滤
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        String userText = advisedRequest.userText();
        
        // 检查是否包含违禁词
        if (containsProhibitedWords(userText)) {
            // 如果包含违禁词，返回友好的拒绝消息
            return AdvisedResponse.builder()
                    .response(createRefusalResponse())
                    .build();
        }
        
        // 如果没有违禁词，继续正常流程
        return chain.nextAroundCall(advisedRequest);
    }

    private boolean containsProhibitedWords(String text) {
        if (text == null) return false;
        
        String lowerText = text.toLowerCase();
        return prohibitedWords.stream()
                .anyMatch(word -> lowerText.contains(word.toLowerCase()));
    }

    private org.springframework.ai.chat.model.ChatResponse createRefusalResponse() {
        // 创建一个友好的拒绝响应
        return new org.springframework.ai.chat.model.ChatResponse(
            java.util.List.of(
                new org.springframework.ai.chat.model.Generation(
                    new org.springframework.ai.chat.messages.AssistantMessage(
                        "抱歉，我不能回答包含不当内容的问题。让我们换个话题，聊聊积极正面的内容吧！"
                    )
                )
            )
        );
    }
}