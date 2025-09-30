package com.wzh.springai.service;

import com.wzh.springai.model.vo.LoverReportVO;

public interface LoverService {
    String chat(String message);

    String loverChatMemory(String message, String chatId);

    String chatLoverFormRAG(String message, String chatId);

    LoverReportVO chatLoverReport(String message, String chatId);

    String chatLoverFormMCP(String message, String chatId);
}
