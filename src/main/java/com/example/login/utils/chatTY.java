package com.example.login.utils;

// 建议dashscope SDK的版本 >= 2.12.0

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class chatTY {
    // 这样加上密钥即可 ！！！
    private static String API_KEY = "sk-530aea6c5e474973a3f67e4f0458f5a7\t";

    public static String getResult(String message)
            throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(message)
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey(API_KEY)
                .model("qwen-plus")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        GenerationResult result = gen.call(param);

        String responseJson = JsonUtils.toJson(result);
        JSONObject responseObj = new JSONObject(responseJson); // 使用org.json的JSONObject
        JSONObject outputObj = responseObj.getJSONObject("output");
        JSONArray choicesArray = outputObj.getJSONArray("choices");
        JSONObject firstChoiceObj = choicesArray.getJSONObject(0);
        JSONObject messageObj = firstChoiceObj.getJSONObject("message");
        String contentStr = messageObj.getString("content");
        return contentStr;
    }
    public static void main(String[] args) {
        try {
            String result = getResult("hahah");
            System.out.println(JsonUtils.toJson(result));
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            // 使用日志框架记录异常信息
            System.err.println("An error occurred while calling the generation service: " + e.getMessage());
        }
        System.exit(0);
    }
}