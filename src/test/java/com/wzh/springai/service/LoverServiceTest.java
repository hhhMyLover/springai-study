package com.wzh.springai.service;

import com.wzh.springai.model.vo.LoverReportVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Slf4j
@SpringBootTest
class LoverServiceTest {

    @Resource
    private LoverService loverService;

    @Test
    void chatLoverReport() {
        String uuid = UUID.randomUUID().toString();
        LoverReportVO loverReportVO = loverService.chatLoverReport("我的名字叫山治，我前段时间和女朋友分手了，现在很伤心，我不知道该怎么做。", uuid);
        Assertions.assertNotNull(loverReportVO);
    }

    @Test
    void chat(){
        String uuid = UUID.randomUUID().toString();
        loverService.loverChatMemory("我的名字叫山治，我前段时间和女朋友分手了，现在很伤心，我不知道该怎么做。", uuid);
        log.info("第一轮对话 conversationId:[{}]",uuid);
        loverService.loverChatMemory("我在最近的日子里应该怎么缓解", uuid);
        log.info("第二轮对话 conversationId:[{}]",uuid);
        loverService.loverChatMemory("我想要走出这段伤心事", uuid);
        log.info("第三轮对话 conversationId:[{}]",uuid);
    }
}