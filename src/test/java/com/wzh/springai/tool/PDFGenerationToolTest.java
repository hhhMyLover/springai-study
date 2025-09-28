package com.wzh.springai.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        String path = pdfGenerationTool.generatePDF("恋爱秘籍.pdf", "时刻关心对方");
        log.info("生成文件的路径:{}",path);
    }
}