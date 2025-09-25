package com.wzh.springai.tool;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.wzh.springai.constant.FileSaveConstant.FILE_SAVE_PATH;

@Component
public class FileOperationTool {

    @Tool(description = "Read file")
    public String readFile(@ToolParam(description = "Name of read to file") String fileName) {
        String savePath = FILE_SAVE_PATH + "/file/" + fileName;
        try {
            File file = FileUtil.file(savePath);
            String read = FileUtil.readUtf8String(file);
            return "File content is:" + read;
        } catch (IORuntimeException e) {
            return "File not found" + e.getMessage();
        }
    }


    @Tool(description = "Write file")
    public String writeFile(@ToolParam(description = "Name of write to file") String fileName,
                            @ToolParam(description = "Content to write to file") String content){
        String savePath = FILE_SAVE_PATH + "/file/" + fileName;
        try {
            File file = FileUtil.file(savePath);
            File writeUtf8String = FileUtil.writeUtf8String(content, file);
            return "Write file success, the file name is :" + writeUtf8String.getName();
        } catch (IORuntimeException e) {
            return "Write file failed" + e.getMessage();
        }
    }
}
