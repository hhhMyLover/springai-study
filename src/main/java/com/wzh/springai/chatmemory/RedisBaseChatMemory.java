package com.wzh.springai.chatmemory;

import cn.hutool.json.JSONUtil;
import com.wzh.springai.model.dto.ConversationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class RedisBaseChatMemory implements ChatMemory {

    private final RedisTemplate<String, Object> redisTemplate;

    // 构造函数注入
    public RedisBaseChatMemory(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        ConversationDTO conversationDTO =(ConversationDTO) redisTemplate.opsForValue().get(conversationId);

        if (conversationDTO==null){
            conversationDTO = new ConversationDTO();
            conversationDTO.setMessages(JSONUtil.toJsonStr(messages));
            conversationDTO.setSessionId(conversationId);
            conversationDTO.setCreateTime(new Date());
            conversationDTO.setUpdateTime(new Date());
        }else{
            List<Object> list = new ArrayList<>();

            list.addAll(messages);
            conversationDTO.setMessages(JSONUtil.toJsonStr(list));
        }
        redisTemplate.opsForValue().set(conversationId,conversationDTO);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        ConversationDTO conversationDTO =(ConversationDTO) redisTemplate.opsForValue().get(conversationId);
        if (conversationDTO !=null && conversationDTO.getMessages() != null){
            List<Message> messages = JSONUtil.toList(conversationDTO.getMessages(), Message.class);
            return messages.stream()
                    .skip(Math.max(messages.size() - lastN, 0))
                    .toList();
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public void clear(String conversationId) {
        redisTemplate.delete(conversationId);
    }
}
