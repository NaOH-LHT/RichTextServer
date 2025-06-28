package com.example.login.service.impl;

import com.example.login.entity.Document;
import com.example.login.entity.User;
import com.example.login.repository.DocumentRepository;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentServiceImpl {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据userId查找文档，如果userId为null则返回所有文档
     */
    public Map<String, Object> findByUserId(Long userId) {
        List<Document> docs = (userId == null) ? documentRepository.findAll() : documentRepository.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Document doc : docs) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", doc.getDocName());
            map.put("date", doc.getAccessTime() != null ? sdf.format(doc.getAccessTime()) : "");
            map.put("owner",userRepository.findByUserId(doc.getUserId()).getNickname());
            result.add(map);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        System.out.println("查询到的文档数量为：" + result.size());
        return response;
    }

    public Map<String, Object> findByKnowledgeBaseId(Long knowledgeBaseId) {
        List<Document> docs = documentRepository.findByKbId(knowledgeBaseId);
        List<Map<String, Object>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Document doc : docs) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", doc.getDocName());
            map.put("date", doc.getAccessTime() != null ? sdf.format(doc.getAccessTime()) : "");
            map.put("owner",userRepository.findByUserId(doc.getUserId()).getNickname());
            result.add(map);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        return response;

    }
}
