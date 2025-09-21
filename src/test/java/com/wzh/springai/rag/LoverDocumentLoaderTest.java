package com.wzh.springai.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoverDocumentLoaderTest {
    @Resource
    private LoverDocumentLoader loader;

    @Test
    void loadDocuments() {
        loader.loadDocuments();
    }
}