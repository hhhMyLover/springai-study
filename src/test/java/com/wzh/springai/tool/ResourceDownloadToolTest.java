package com.wzh.springai.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        String path = resourceDownloadTool.downloadResource("https://www.baidu.com/img/bd_logo1.png", "baidu.png");
        log.info("下载的文件路径为:{}",path);
    }
}