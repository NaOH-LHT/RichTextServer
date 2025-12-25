package com.example.login.controller;

import com.example.login.service.ChatAI;
import com.example.login.service.impl.ChatAIimpl;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/editor")
@CrossOrigin(origins = "*") // 允许所有来源的请求
public class SummaryController {

    private final ChatAI AI = new ChatAIimpl();

    @PostMapping("/summary")
    public String getSummary(@RequestBody Map<String, String> request) throws IOException {
        try {
            // 从请求体中获取 content 字段
            String content = request.get("content");
            System.out.println("接收到的 content: " + content); // 打印日志确认
            return AI.getSummary(content);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
