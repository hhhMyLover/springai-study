package com.wzh.springai.tool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.wzh.springai.constant.FileSaveConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.File;

/**
 * 使用hutool 下载资源
 */
public final class ResourceDownloadTool {

    @Tool(description = "Download a resource from a given URL")
    public String downloadResource(@ToolParam(description = "URL of the resource to download") String url, @ToolParam(description = "Name of the file to save the downloaded resource") String fileName) {
        String fileDir = FileSaveConstant.FILE_SAVE_PATH + "/download";
        String filePath = fileDir + "/" + fileName;
        try {
            // 创建目录
            FileUtil.mkdir(fileDir);
            // 使用 Hutool 的 downloadFile 方法下载资源
            HttpUtil.downloadFile(url, new File(filePath));
            return "Resource downloaded successfully to: " + filePath;
        } catch (Exception e) {
            return "Error downloading resource: " + e.getMessage();
        }
    }
}
