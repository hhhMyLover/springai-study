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
}