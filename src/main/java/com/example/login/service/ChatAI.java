package com.example.login.service;

import java.io.IOException;

public interface ChatAI {

    // 获取AI摘要
    String getSummary(String doc) throws IOException;

}
