package com.project.service.impl;

import com.project.common.ErrorCode;
import com.project.exception.BusinessException;
import com.project.pojo.MyFile;
import com.project.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 下水道的小老鼠
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public List<MyFile> getFiles() {
        String url = "http://139.155.147.15/example/";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件列表获取异常");
        }
        Elements elements = doc.select("tr.tableRow");
        List<MyFile> myFileList = new ArrayList<>();
        for (Element element : elements) {
            MyFile myFile = new MyFile();

            String fileUrl = element.select(".stored-edit").get(0).attr("href");
            String fileName = element.select("span").first().text();

            String downloadUrl = element.select(".downloadContentCellShift")
                    .select("a")
                    .attr("href");
            String deleteUrl = url + StringUtils.replace(fileUrl,"fileName","filename");

            int index = fileName.lastIndexOf(".");
            String type = fileName.substring(index + 1);

            myFile.setFileName(fileName);
            myFile.setFileUrl(url + fileUrl);
            myFile.setFileType(type);
            myFile.setDownloadUrl(url + downloadUrl);
            myFile.setDeleteUrl(deleteUrl);
            System.out.println(myFile);
            myFileList.add(myFile);
        }
        return myFileList;
    }

    @Override
    public boolean deleteFile(String deleteUrl) {
        try {
            String newUrl = StringUtils.replace(deleteUrl, "editor", "file");

            URL url = new URL(newUrl);
            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "请尽快通知项目管理员");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.toString());
        }
    }
}
