package com.wzh.springai.config;

import com.wzh.springai.rag.LoverDocumentLoader;
import com.wzh.springai.rag.MyKeywordEnricher;
import com.wzh.springai.rag.MyTokenTextSplitter;
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

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeywordEnricher myKeywordEnricher;

    @Bean
    VectorStore loverVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        List<Document> documents = loverDocumentLoader.loadDocuments();

        // 不建议用 会被拆分混乱
//        List<Document> splitDocuments = myTokenTextSplitter.splitCustomized(documents);
        // 添加关键词
        List<Document> enrichDocuments = myKeywordEnricher.enrichDocuments(documents);
        vectorStore.add(enrichDocuments);
        return vectorStore;
    }
}
