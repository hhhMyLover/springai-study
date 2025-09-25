package com.wzh.springai.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class WebSearchToolTest {

    @Autowired
    private WebSearchTool webSearchTool;

    @Test
    void searchBaidu() {
        String result = webSearchTool.searchBaidu("搜索一下面包制作过程");
        log.info("搜索到的内容是:{}",result);
    }
}