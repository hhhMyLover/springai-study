package com.wzh.springai.service.impl;

import com.wzh.springai.advisor.LoverAdvisor;
import com.wzh.springai.model.vo.LoverReportVO;
import com.wzh.springai.service.LoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
public class LoverServiceImpl implements LoverService {

    private static final Logger log = LoggerFactory.getLogger(LoverServiceImpl.class);
    private final ChatClient chatClient;

    private final ChatClient chatClientWithMemory;

    // 新特性，快速定义类
//    record LoverReport(String title, List<String> suggestions) {
//    }

    public LoverServiceImpl(ChatModel dashscopeChatModel) {
        ChatMemory chatMemory = new InMemoryChatMemory();
        // 初始化带记忆功能的 ChatClient
        this.chatClientWithMemory = ChatClient.builder(dashscopeChatModel)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new LoverAdvisor()
//                        new ReReadingAdvisor()
                )
                .defaultSystem("你是一个恋爱助手，帮我解决我的恋爱问题并且进行情感疏导")
                .build();

        // 初始化普通 ChatClient
        this.chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem("你是一个恋爱助手，帮我解决我的恋爱问题并且进行情感疏导")
                .defaultAdvisors(
                        new LoverAdvisor()
                )
                .build();
    }

    @Override
    public String chat(String message) {
        ChatResponse chatResponse = chatClient.prompt(message)
                .call()
                .chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public String loverChatMemory(String message, String chatId) {
        ChatResponse chatResponse = chatClientWithMemory.prompt(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        return content;
    }

    @Override
    public LoverReportVO chatLoverReport(String message, String chatId) {
        LoverReportVO loverReport = chatClientWithMemory.prompt(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoverReportVO.class);
        log.info("springAi chatLoverReport data:report{}",loverReport);
        return loverReport;
    }
}
