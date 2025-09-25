package com.wzh.springai.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TerminalOperationToolTest {

    @Test
    void executeTerminalCommand() {

        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        String dir = terminalOperationTool.executeTerminalCommand("1");
        log.info("终端执行的结果为:{}",dir);
    }
}