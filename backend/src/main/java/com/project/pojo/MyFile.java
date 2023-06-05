package com.project.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 下水道的小老鼠
 */
@Data
public class MyFile implements Serializable {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String downloadUrl;
    private String deleteUrl;
    private static final long serialVersionUID = 3L;
}
