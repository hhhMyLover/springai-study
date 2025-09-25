package com.wzh.springai.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class WebParseToolTest {

    @Test
    void parseWeb() {
        WebParseTool webParseTool = new WebParseTool();
        String parseWeb = webParseTool.parseWeb("https://www.baidu.com");
        Assertions.assertNotNull(parseWeb);
        log.info("parseWeb: {}", parseWeb);
    }
}