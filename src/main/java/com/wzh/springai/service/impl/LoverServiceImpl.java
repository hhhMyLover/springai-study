package com.wzh.springai.service.impl;

import com.wzh.springai.model.vo.LoverReportVO;
import com.wzh.springai.service.LoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class LoverServiceImpl implements LoverService {

    private static final Logger log = LoggerFactory.getLogger(LoverServiceImpl.class);
    private final ChatClient chatClient;

    public LoverServiceImpl(ChatModel dashscopeChatModel) {
        // 初始化普通 ChatClient
        this.chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem("你是一个温柔的聊天助手，请用中文回复。")
                .build();
    }

    @Override
    public String chat(String message) {
        try {
            ChatResponse chatResponse = chatClient.prompt(message)
                    .call()
                    .chatResponse();
            return chatResponse.getResult().getOutput().getText();
        } catch (Exception e) {
            log.error("Chat error: ", e);
            return "抱歉，我现在无法回复您的消息。请稍后再试。";
        }
    }

    @Override
    public String loverChatMemory(String message, String chatId) {
        // 简化版本，暂时不实现记忆功能
        return chat(message);
    }

    @Override
    public String chatLoverFormRAG(String message, String chatId) {
        // 简化版本，暂时不实现RAG功能
        return chat(message);
    }

    @Override
    public LoverReportVO chatLoverReport(String message, String chatId) {
        // 简化版本，返回固定格式
        LoverReportVO report = new LoverReportVO();
        report.setTitle("聊天总结");
        report.setSuggestions(java.util.Arrays.asList("继续愉快的聊天吧！"));
        return report;
    }
}