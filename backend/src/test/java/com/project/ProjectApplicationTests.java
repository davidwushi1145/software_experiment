package com.project;

import com.project.common.BaseResponse;
import com.project.common.ErrorCode;
import com.project.exception.BusinessException;
import com.project.pojo.*;
import com.project.service.UserService;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
class ProjectApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(0L);
        user.setUsername("dog");
        user.setUserAccount("root");
        user.setAvatarUrl("https://cn.bing.com/images/search?q=%E5%A4%B4%E5%83%8F%E5%9B%BE%E7%89%87&FORM=IQFRBA&id=294EC054FF50E8CDA64277289E2B4E18466EE9E5");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("1234567890");
        user.setEmail("1111111111");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void testRegister() {
        String userAccount = "bilibili";
        String userPassword = "";
        String checkPassword = "123456789";
        String planetCode = "20230507";

        //判空
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        //账号长度
        userAccount = "sb";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        //密码长度
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        //判断账号是否有特殊字符
        userAccount = "bili bili";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        //两次密码不相同
        userPassword = "222233338";
        checkPassword = "5555555555";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        //账号以注册过
        userAccount = "root";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        //正常插入
        userAccount = "bilibili";
        userPassword = "222233335";
        checkPassword = "222233335";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);
    }

    @Test
    void testCode() {
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        if (simpleDateFormat.format(date).equals("qweasd")) {
            System.out.println("hhh");
        } else {
            System.out.println("hgg");
        }
    }

    /**
     * 测试与 collabora office 连接
     */
    @Test
    void testClient() {
        String accessKey = "4wa3gkaest05pmvubg1lmzca1i2k4jsb";
        String secretKey = "l2hv1x2p6qqgwuiixxdw8wbsikdc5ggc";
        SimpleChat simpleChat = new SimpleChat(accessKey, secretKey);
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1661721207793340417L);
        devChatRequest.setMessage("鱼皮");
        BaseResponse<DevChatResponse> devChatResponseBaseResponse = simpleChat.doChat(devChatRequest);
        DevChatResponse data = devChatResponseBaseResponse.getData();
        System.out.println(devChatResponseBaseResponse);
        if (data != null) {
            String content = data.getContent();
            System.out.println(content);
        }
//        https://www.yucongming.com/model/1667385837890883585?inviteUser=1661721207793340417
//        String url = "http://139.155.127.29/example/";
//        Document doc;
//        try {
//            doc = Jsoup.connect(url).get();
//        } catch (IOException e) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件列表获取异常");
//        }
//        Elements elements = doc.select("tr.tableRow");
//        List<MyFile> myFileList = new ArrayList<>();
//        for (Element element : elements) {
//            MyFile myFile = new MyFile();
//
//            String fileUrl = element.select(".stored-edit").get(0).attr("href");
//            String fileName = element.select("span").first().text();
//
//            String downloadUrl = element.select(".downloadContentCellShift")
//                    .select("a")
//                    .attr("href");
//            String deleteUrl = url + fileUrl;
//
//            int index = fileName.lastIndexOf(".");
//            String type = fileName.substring(index + 1);
//
//            myFile.setFileName(fileName);
//            myFile.setFileUrl(url + fileUrl);
//            myFile.setFileType(type);
//            myFile.setDownloadUrl(url + downloadUrl);
//            myFile.setDeleteUrl(deleteUrl);
//            System.out.println(myFile);
//            myFileList.add(myFile);
//        }
    }

    @Test
    void textChat(){
        String accessKey = "4wa3gkaest05pmvubg1lmzca1i2k4jsb";
        String secretKey = "l2hv1x2p6qqgwuiixxdw8wbsikdc5ggc";
        SimpleChat simpleChat = new SimpleChat(accessKey, secretKey);
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1667385837890883585L);
        devChatRequest.setMessage("Java学习路线");
        BaseResponse<DevChatResponse> devChatResponseBaseResponse = simpleChat.doChat(devChatRequest);
        DevChatResponse data = devChatResponseBaseResponse.getData();
        System.out.println(devChatResponseBaseResponse);
        if (data != null) {
            String content = data.getContent();
            System.out.println(content);
        }
    }
}
