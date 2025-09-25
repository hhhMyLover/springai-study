package com.wzh.springai.tool;

import com.wzh.springai.model.config.SearchApi;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebSearchTool {

    @Autowired
    private SearchApi searchApi;

    /**
     * 执行百度搜索
     *
     * @param query 搜索关键词
     * @return 搜索结果
     */
    @Tool(description = "Search Web API function by AI")
    public String searchBaidu(@ToolParam(description = "Search query content") String query) {
        String result = "";

        try {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.get(searchApi.getApiUrl()).newBuilder();
            urlBuilder.addQueryParameter("engine", "baidu");
            urlBuilder.addQueryParameter("q", query);
            urlBuilder.addQueryParameter("api_key", searchApi.getApiKey());

            Request request = new Request.Builder()
                    .url(urlBuilder.build())
                    .build();

            Response response = client.newCall(request).execute();
            result = response.body().string();

        } catch (Exception e) {
            return "search api is error: " + e.getMessage();
        }

        return "search success result :" + result;
    }
}