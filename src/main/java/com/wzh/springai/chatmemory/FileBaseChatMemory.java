package com.wzh.springai.chatmemory;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于文件的聊天记忆实现 - 将聊天历史保存到文件中
 */
public class FileBaseChatMemory implements ChatMemory {

    private final String basePath;

    public FileBaseChatMemory(String basePath) {
        this.basePath = basePath;
        // 确保目录存在
        try {
            Files.createDirectories(Paths.get(basePath));
        } catch (IOException e) {
            throw new RuntimeException("无法创建聊天记忆存储目录", e);
        }
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        Path filePath = getFilePath(conversationId);
        try {
            // 获取现有消息
            List<Message> allMessages = new ArrayList<>(get(conversationId, Integer.MAX_VALUE));
            // 添加新消息
            allMessages.addAll(messages);
            
            // 保存到文件
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
                oos.writeObject(allMessages);
            }
        } catch (IOException e) {
            throw new RuntimeException("保存聊天记忆失败", e);
        }
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        Path filePath = getFilePath(conversationId);
        
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            @SuppressWarnings("unchecked")
            List<Message> allMessages = (List<Message>) ois.readObject();
            
            // 返回最后N条消息
            int start = Math.max(0, allMessages.size() - lastN);
            return new ArrayList<>(allMessages.subList(start, allMessages.size()));
            
        } catch (IOException | ClassNotFoundException e) {
            // 如果读取失败，返回空列表
            return new ArrayList<>();
        }
    }

    @Override
    public void clear(String conversationId) {
        Path filePath = getFilePath(conversationId);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("清除聊天记忆失败", e);
        }
    }

    private Path getFilePath(String conversationId) {
        return Paths.get(basePath, conversationId + ".chat");
    }
}