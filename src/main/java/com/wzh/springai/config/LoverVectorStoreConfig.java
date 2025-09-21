package com.wzh.springai.config;

import com.wzh.springai.rag.LoverDocumentLoader;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 配置向量数据库
 */
@Configuration
public class LoverVectorStoreConfig {

    @Resource
    private LoverDocumentLoader loverDocumentLoader;

    @Bean
    VectorStore LoverVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        List<Document> documents = loverDocumentLoader.loadDocuments();
        vectorStore.add(documents);
        return vectorStore;
    }
}
