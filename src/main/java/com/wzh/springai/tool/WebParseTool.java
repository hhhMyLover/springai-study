package com.wzh.springai.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class WebParseTool {

    @Tool(description = "Parse web content by url")
    public String parseWeb(@ToolParam(description = "Url of web content") String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return "Parse success url , content is :" + document.html();
        } catch (Exception e) {
            return "Error parsing web content";
        }
    }
}
