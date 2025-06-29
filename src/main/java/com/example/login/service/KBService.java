package com.example.login.service;

import com.example.login.entity.KnowledgeBaseDTO;

import java.util.Map;

public interface KBService {
    Map<String, Object> getKnowledgeBaseList(String userName);

    Map<String, Object> addKnowledgeBase(KnowledgeBaseDTO dto);

    Map<String, Object> deleteKnowledgeBase(String name);

    Map<String, Object> renameKnowledgeBase(String oldName, String newName);

    Map<String, Object> queryKnowledgeBase(String name, String owner, String startDate, String endDate);

    Map<String,Object> queryRightKnowledgeBase(Long userId);
}
