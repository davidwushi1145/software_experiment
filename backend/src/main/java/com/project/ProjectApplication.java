package com.project;

import com.project.common.BaseResponse;
import com.project.pojo.DevChatRequest;
import com.project.pojo.DevChatResponse;
import com.project.pojo.SimpleChat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动 SpringBoot
 *
 * @author 下水道的小老鼠
 */
@SpringBootApplication
public class ProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
