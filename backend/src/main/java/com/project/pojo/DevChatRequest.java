package com.project.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 对话请求
 *
 * @author 下水道的小老鼠
 */
@Data
public class DevChatRequest implements Serializable {

    /**
     * 模型 id
     */
    private Long modelId;

    /**
     * 消息
     */
    private String message;

    private static final long serialVersionUID = 1L;

}