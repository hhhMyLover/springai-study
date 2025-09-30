package com.wzh.imagesearchmcpserver.tools;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageSearchToolTest {

    @Resource
    private ImageSearchTool imageSearchTool;

    @Test
    void searchImages() {
        String images = imageSearchTool.searchImages("cat");
        System.out.println(images);
    }
}