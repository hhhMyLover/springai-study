package com.wzh.springai.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于云存储的知识库检索
 */
@Configuration
@Slf4j
public class LoverCloudStoreConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String dashscopeApiKey;

    private final String INDEX_NAME = "恋爱大师";

    @Bean
    public Advisor loverCloudStoreAdvisor(){
        DashScopeApi dashScopeApi = new DashScopeApi(dashscopeApiKey);
        DocumentRetriever retriever = new DashScopeDocumentRetriever(dashScopeApi,
                DashScopeDocumentRetrieverOptions.builder().withIndexName(INDEX_NAME).build());

        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(retriever)
                .build();
    }
}
