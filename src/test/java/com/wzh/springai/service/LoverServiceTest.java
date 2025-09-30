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
        loverService.loverChatMemory("我的名字叫山治，我前段时间和女朋友分手了，现在很伤心，我不知道你妈妈该怎么做。", uuid);
        log.info("第一轮对话 conversationId:[{}]",uuid);
        loverService.loverChatMemory("我在最近的日子里应该怎么缓解", uuid);
        log.info("第二轮对话 conversationId:[{}]",uuid);
        loverService.loverChatMemory("我想要走出这段伤心事", uuid);
        log.info("第三轮对话 conversationId:[{}]",uuid);
    }


    @Test
    void chatLoverFormRAG() {
        String uuid = UUID.randomUUID().toString();
        loverService.chatLoverFormRAG("我的名字叫山治，我前段时间和女朋友分手了，现在很伤心，我不知道该怎么做。帮我搜索网络上关于失恋的图片并保存", uuid);
//        loverService.chatLoverFormRAG("我是一个单身人士，最近我想要谈恋爱，但是不知道从何提升个人魅力。请你给我一点建议,并且将这些建议生成一份pdf文档给我并且不要markdown格式，而是要自动生成符合pdf的格式。",uuid);
    }

    @Test
    void chatLoverFormMCP() {
        String uuid = UUID.randomUUID().toString();
        // 尝试更明确地指示需要使用工具
        loverService.chatLoverFormMCP("请使用地图搜索功能，在武汉江夏区秋果S酒店附近查找一家评分较高的川菜餐厅，并提供详细地址和联系方式", uuid);
    }

    @Test
    void chatLoverFormLocalMCP() {
        String uuid = UUID.randomUUID().toString();
        // 尝试更明确地指示需要使用工具
        loverService.chatLoverFormMCP("请帮我查找关于花的图片", uuid);
    }
}