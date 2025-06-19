package com.example.login.service.impl;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.login.service.ChatAI;
import com.example.login.utils.chatTY;

import java.io.IOException;

public class ChatAIimpl implements ChatAI {

    private static chatTY tonyiApi = new chatTY();// 通义千问模型的API接口

    // 获取大模型返回信息
    public String getSummary(String doc) throws IOException {
        return ChatTonyi(doc);
    }

    // 使用通义千问API进行文本处理
    private String ChatTonyi(String inputText) throws IOException {
        try {
            String text = inputText; // 将输入文本赋值给变量text
            String tmp = tonyiApi.getResult(text); // 调用tonyiApi处理文本并获取结果
            return tmp; // 返回处理后的文本结果
        } catch (NoApiKeyException | InputRequiredException e) {
            e.printStackTrace(); // 如果发生API密钥异常或输入所需异常，打印堆栈跟踪
            return "失败：" + e.getMessage(); // 返回失败信息和异常消息
        }
    }

}
