package com.project.pojo;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.project.common.BaseResponse;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.project.utils.SignUtils.genSign;


/**
 *
 * @author 下水道的小老鼠
 */
public class SimpleChat {
    private static final String HOST = "https://www.yucongming.com/api/dev";
    private final String accessKey;
    private final String secretKey;

    public SimpleChat(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 对话
     *
     * @param devChatRequest 用户问题
     * @return 回答
     */
    public BaseResponse<DevChatResponse> doChat(DevChatRequest devChatRequest) {
        String url = HOST + "/chat";
        String json = JSONUtil.toJsonStr(devChatRequest);
        String result = HttpRequest.post(url)
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute()
                .body();
        TypeReference<BaseResponse<DevChatResponse>> typeRef = new TypeReference<BaseResponse<DevChatResponse>>() {};
        return JSONUtil.toBean(result, typeRef, false);
    }

    /**
     * 获取请求头
     *
     * @param body 请求参数
     * @return 请求头
     */
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        String encodedBody = URLEncodeUtil.encode(body, StandardCharsets.UTF_8);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(encodedBody, secretKey));
        return hashMap;
    }


}
