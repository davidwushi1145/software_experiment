package com.project.service;

import com.project.pojo.MyFile;

import java.util.List;

/**
 * @author 下水道的小老鼠
 */
public interface FileService {

    /**
     * 获取所有的文件
     *
     * @return 返回文件列表
     */
    List<MyFile> getFiles();

    /**
     * 删除文件
     *
     * @return 返回删除结果
     */
    boolean deleteFile(String deleteUrl);
}
