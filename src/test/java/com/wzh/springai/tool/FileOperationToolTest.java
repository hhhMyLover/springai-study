package com.wzh.springai.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FileOperationToolTest {

    @Autowired
    private FileOperationTool fileOperationTool;

    @Test
    void readFile() {
        String string = fileOperationTool.readFile("恋爱日记");
        log.info("读取到的内容:{}",string);
    }

    @Test
    void writeFile() {
        String string = fileOperationTool.writeFile("恋爱日记", "一个甜甜的恋爱~");
        log.info("写入的文件信息:{}",string);
    }
}