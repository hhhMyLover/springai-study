package com.wzh.springai.tool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * Web搜索工具 - 演示Spring AI的函数调用功能
 */
@Configuration
public class WebSearchTool {

    /**
     * 模拟网络搜索功能
     */
    @Bean
    @Description("搜索网络信息，获取相关内容")
    public Function<SearchRequest, SearchResult> webSearch() {
        return request -> {
            // 这里只是模拟搜索，实际应用中会调用真实的搜索API
            String result = "关于 '" + request.query() + "' 的搜索结果：\n" +
                          "1. 这是一个模拟的搜索结果\n" +
                          "2. 在实际应用中，这里会调用真实的搜索引擎API\n" +
                          "3. 搜索时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            return new SearchResult(result, request.query());
        };
    }

    /**
     * 获取当前时间
     */
    @Bean
    @Description("获取当前日期和时间")
    public Function<Void, String> getCurrentTime() {
        return unused -> {
            return "当前时间是：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
        };
    }

    /**
     * 搜索请求参数
     */
    public record SearchRequest(String query, int maxResults) {
        public SearchRequest(String query) {
            this(query, 5);
        }
    }

    /**
     * 搜索结果
     */
    public record SearchResult(String content, String query) {}
}