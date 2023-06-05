package com.project.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.common.BaseResponse;
import com.project.common.ErrorCode;
import com.project.common.ResultUtils;
import com.project.exception.BusinessException;
import com.project.pojo.MyFile;
import com.project.pojo.User;
import com.project.service.FileService;
import com.project.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.project.contant.UserStaticNumber.ADMIN_ROLE;
import static com.project.contant.UserStaticNumber.USER_LOGIN_STATE;

/**
 * @author 下水道的小老鼠
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("/search")
    public BaseResponse<List<MyFile>> searchFile(String fileName, HttpServletRequest request) {
        List<MyFile> myFileList = fileService.getFiles();
        return ResultUtils.success(myFileList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody String deleteUrl, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user.getUserRole() != ADMIN_ROLE) {
            throw new BusinessException(ErrorCode.NO_AUTH, "你没有权限删除任何一个文件");
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(deleteUrl).getAsJsonObject();
        deleteUrl = jsonObject.get("deleteUrl").getAsString();
        fileService.deleteFile(deleteUrl);
        return ResultUtils.success(true);
    }
}
