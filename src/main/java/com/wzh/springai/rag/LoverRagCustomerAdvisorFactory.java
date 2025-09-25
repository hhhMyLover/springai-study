package com.wzh.springai.rag;

import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;

/**
 * 恋爱RAG顾问工厂 - 创建针对恋爱场景的RAG顾问
 */
public class LoverRagCustomerAdvisorFactory {

    /**
     * 创建恋爱相关的RAG顾问
     * @param vectorStore 向量存储  
     * @param userStatus 用户状态（如：单身、恋爱中、已婚等）
     * @return QuestionAnswerAdvisor
     */
    public static QuestionAnswerAdvisor createLoverRagCustomerAdvisor(VectorStore vectorStore, String userStatus) {
        // 使用简单的构造函数创建QuestionAnswerAdvisor
        return new QuestionAnswerAdvisor(vectorStore);
    }

    /**
     * 创建简单的RAG顾问
     * @param vectorStore 向量存储
     * @return QuestionAnswerAdvisor
     */
    public static QuestionAnswerAdvisor createSimpleRagAdvisor(VectorStore vectorStore) {
        return new QuestionAnswerAdvisor(vectorStore);
    }
}