package com.wzh.imagesearchmcpserver.tools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ImageSearchTool {

    @Value("${pexels.api-key}")
    private String apiKey;

    /**
     * 从Pexels搜索图片
     *
     * @param query 搜索关键词
     * @return 图片URL列表
     */
    @Tool(description = "Search images from Pexels API")
    public String searchImages(
            @ToolParam(description = "Image search keyword") String query) {

        try {
            if (StrUtil.isBlank(apiKey)) {
                throw new RuntimeException("Please set your Pexels API key.");
            }
            if (StrUtil.isBlank(query)) {
                throw new RuntimeException("Please provide a search query.");
            }
            return String.join(",", getImageUrl(query));
        }catch (Exception e){
            log.error("Error occurred while searching images: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private List<String> getImageUrl(String query) {
        String url = "https://api.pexels.com/v1/search";

        List<String> imageUrls;
        try (HttpResponse response = HttpRequest.get(url)
                .header("Authorization", apiKey)
                .form("query", query)
                .form("per_page", Math.min(10, 80))
                .execute()) {

            imageUrls = new ArrayList<>();

            if (response.isOk()) {
                String responseBody = response.body();
                imageUrls = JSONUtil.parseObj(responseBody)
                        .getJSONArray("photos").stream()
                        .map(JSONUtil::parseObj)
                        .map(item -> item.getJSONObject("src").getStr("medium"))
                        .filter(StrUtil::isNotBlank)
                        .toList();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return imageUrls;
    }
}