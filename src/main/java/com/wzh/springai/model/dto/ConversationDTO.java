package com.wzh.springai.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ConversationDTO implements Serializable {

    private static final long serialVersionUID = 1837142795654390046L;

    private String sessionId;          // 会话唯一标识
    private String messages;    // 消息列表
    private Date createTime;  // 创建时间
    private Date updateTime;  // 更新时间
}
