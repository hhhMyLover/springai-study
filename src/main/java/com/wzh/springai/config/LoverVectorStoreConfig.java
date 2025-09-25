package com.wzh.springai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 向量存储配置 - 用于RAG功能
 */
@Configuration
public class LoverVectorStoreConfig {

    @Autowired(required = false)
    private EmbeddingModel embeddingModel;

    /**
     * 恋爱知识向量存储
     */
    @Bean("loverVectorStore")
    @ConditionalOnMissingBean(name = "loverVectorStore")
    public VectorStore loverVectorStore() {
        if (embeddingModel != null) {
            return SimpleVectorStore.builder(embeddingModel).build();
        } else {
            // 如果没有嵌入模型，返回一个模拟的向量存储
            return new MockVectorStore();
        }
    }

    /**
     * 模拟向量存储 - 用于演示目的
     */
    private static class MockVectorStore implements VectorStore {
        
        @Override
        public void add(java.util.List<org.springframework.ai.document.Document> documents) {
            // 模拟添加文档
        }

        @Override
        public void delete(java.util.List<String> idList) {
            // 模拟删除
        }

        @Override
        public void delete(org.springframework.ai.vectorstore.filter.Filter.Expression expression) {
            // 模拟按表达式删除
        }

        @Override
        public java.util.List<org.springframework.ai.document.Document> similaritySearch(String query) {
            // 返回模拟的相似文档
            return java.util.List.of(
                org.springframework.ai.document.Document.builder()
                    .text("这是关于恋爱建议的模拟文档内容。在恋爱中，沟通是非常重要的。")
                    .metadata("source", "mock-document")
                    .build()
            );
        }

        @Override
        public java.util.List<org.springframework.ai.document.Document> similaritySearch(
                org.springframework.ai.vectorstore.SearchRequest request) {
            return similaritySearch(request.getQuery());
        }
    }
}