package com.wzh.springai.service.impl;

import com.wzh.springai.advisor.LoverAdvisor;
import com.wzh.springai.advisor.ProhibitedWordsAdvisor;
import com.wzh.springai.chatmemory.FileBaseChatMemory;
import com.wzh.springai.model.vo.LoverReportVO;
import com.wzh.springai.service.LoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
public class LoverServiceImpl implements LoverService {

    private static final Logger log = LoggerFactory.getLogger(LoverServiceImpl.class);
    private final ChatClient chatClient;

    private final ChatClient chatClientWithMemory;

    @jakarta.annotation.Resource
    private VectorStore loverVectorStore;

    @jakarta.annotation.Resource
    private VectorStore loverPgVectorStore;

    @jakarta.annotation.Resource
    private Advisor loverCloudStoreAdvisor;

    @jakarta.annotation.Resource
    private ToolCallback[] allTools;

    @jakarta.annotation.Resource
    private ToolCallbackProvider toolCallbackProvider;

    // 新特性，快速定义类
//    record LoverReport(String title, List<String> suggestions) {
//    }

    public LoverServiceImpl(ChatModel dashscopeChatModel,
                            RedisTemplate<String ,Object> redisTemplate,
                            @Value("classpath:/prompts/system-message.st") Resource systemResource) {
//        ChatMemory chatMemory = new InMemoryChatMemory();
        String filePath = System.getProperty("user.dir") + "/tmp/chat-memory";
        FileBaseChatMemory chatMemory = new FileBaseChatMemory(filePath);

//        RedisBaseChatMemory redisBaseChatMemory = new RedisBaseChatMemory(redisTemplate);
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("type", "温柔");
        templateParams.put("language", "中文");
        String render = systemPromptTemplate.render(templateParams);

        // 初始化带记忆功能的 ChatClient
            this.chatClientWithMemory = ChatClient.builder(dashscopeChatModel)
                .defaultAdvisors(
                        // 测试自定义记忆功能
                        new MessageChatMemoryAdvisor(chatMemory),
                        new LoverAdvisor(),
                        new ProhibitedWordsAdvisor()
//                        new ReReadingAdvisor()
                )
                .defaultSystem(render)
                .build();

        // 初始化普通 ChatClient
        this.chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(render)
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

    @Override
    public String chatLoverFormRAG(String message, String chatId) {
        ChatResponse chatResponse = chatClientWithMemory.prompt(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(
                        new QuestionAnswerAdvisor(loverVectorStore)
                        // 增强顾问
//                        loverCloudStoreAdvisor,
                        // 使用线上数据库保存知识库
//                        new QuestionAnswerAdvisor(loverPgVectorStore)
//                        LoverRagCustomerAdvisorFactory.createLoverRagCustomerAdvisor(
//                                loverVectorStore,
//                                "单身"
//                        )
                )
                .tools(allTools)
                .call()
                .chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public String chatLoverFormMCP(String message, String chatId) {
        ChatResponse chatResponse = chatClientWithMemory.prompt(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(
                        new QuestionAnswerAdvisor(loverVectorStore)
                )
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }
}
