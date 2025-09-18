package com.wzh.springai.chatmemory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileBaseChatMemory implements ChatMemory {

    private final String BASE_PATH;

    private static final Kryo kryo = new Kryo();


    public FileBaseChatMemory(String basePath){
        this.BASE_PATH = basePath;
        File file = new File(basePath);
        if (!file.exists()){
            boolean res = file.mkdirs();
            log.info("创建目录结果：{}",res);
        }
    }

    static {
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
    }

    private List<Message> getOrCreateConversationMessage(String conversationId){
        File file = getConversationFile(conversationId);
        List<Message> messages = new ArrayList<>();
        if (file.exists()){
            try (Input input = new Input(new FileInputStream(file))){

                messages = kryo.readObject(input, ArrayList.class);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return messages;
    }

    private void saveConversationMessageFile(String conversationId,List<Message> messageList){
        File file = getConversationFile(conversationId);
        try (Output output = new Output(new FileOutputStream(file))){

            kryo.writeObject(output,messageList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 每个会话单独创建文件
     * @param conversationId
     * @return
     */
    private File getConversationFile(String conversationId){
        return new File(BASE_PATH, conversationId);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<Message> messageList = getOrCreateConversationMessage(conversationId);
        messageList.addAll(messages);
        saveConversationMessageFile(conversationId,messageList);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<Message> messages = getOrCreateConversationMessage(conversationId);
        return messages.stream()
                .skip(Math.max(messages.size() - lastN, 0))
                .toList();
    }

    @Override
    public void clear(String conversationId) {
        File file = getConversationFile(conversationId);
        if (file.exists()){
            file.delete();
        }
    }
}
