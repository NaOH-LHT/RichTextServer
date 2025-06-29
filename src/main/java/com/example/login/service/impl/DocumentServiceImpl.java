package com.example.login.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.login.entity.Document;
import com.example.login.entity.EditRight;
import com.example.login.repository.DocumentRepository;
import com.example.login.repository.EditRightRepository;
import com.example.login.repository.UserRepository;
import com.example.login.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EditRightRepository editRightRepository;

    @Override
    public Map<String, Object> deleteDocument(String name) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 先查找文档是否存在
            Document document = documentRepository.findByDocName(name);
            if (document == null) {
                result.put("success", false);
                result.put("message", "文档不存在");
                return result;
            }

            // 删除文档
            documentRepository.deleteByDocName(name);
            result.put("success", true);
            result.put("message", "文档删除成功");
            return result;
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "文档删除失败: " + e.getMessage());
            return result;
        }
    };

    @Override
    public Map<String, Object> renameDocument(String oldName, String newName) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 检查旧文档是否存在
            Document oldDocument = documentRepository.findByDocName(oldName);
            if (oldDocument == null) {
                result.put("success", false);
                result.put("message", "原文档不存在");
                return result;
            }

            // 检查新名称是否已存在
            Document existingDocument = documentRepository.findByDocName(newName);
            if (existingDocument != null) {
                result.put("success", false);
                result.put("message", "新文档名称已存在");
                return result;
            }

            // 更新文档名称
            oldDocument.setDocName(newName);
            documentRepository.save(oldDocument);

            result.put("success", true);
            result.put("message", "文档重命名成功");
            return result;
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "文档重命名失败: " + e.getMessage());
            return result;
        }
    };

    /**
     * 该用户可以访问的文档
     */
    private List<Map<String, Object>> getDocumentRight(Long userId) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<EditRight> kbList = editRightRepository.findByUserId(userId);// 可访问知识库
        for (EditRight kb : kbList) {
            // 得到知识库中所有文档

            List<Document> docs = documentRepository.findBykbId(kb.getKbId());
            System.out.print("======================知识库");
            System.out.println(kb.getKbId());
            for (Document doc : docs) {
                Map<String, Object> map = new HashMap<>();
                System.out.println("文档名称");
                System.out.println(doc.getDocName());

                map.put("name", doc.getDocName());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("date", doc.getAccessTime() != null ? sdf.format(doc.getAccessTime()) : "");
                map.put("owner", userRepository.findByUserId(doc.getUserId()).getNickname());
                map.put("ownerId", doc.getUserId());
                map.put("id", doc.getDocId());
                map.put("accessTime", sdf.format(doc.getAccessTime()));
                map.put("create_time", sdf.format(doc.getCreateTime()));
                map.put("isCollaborative", doc.getIsCollaborative());
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 根据userId查找文档，该用户能访问的所有文档
     */
    public Map<String, Object> findByUserId(Long userId) {
        List<Map<String, Object>> result = getDocumentRight(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        System.out.println("查询到的文档数量为：" + result.size());
        return response;
    }

    /**
     * 根据条件筛选用户文档: 文档名,所有者名字(NickName,时间(最近查看)
     */
    public List<Map<String, Object>> searchDocument(String docName, String nickName, String begin, String end,
            Long userId) {

        List<Map<String, Object>> documentRight = getDocumentRight(userId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> filteredDocuments = new ArrayList<>();

        // 如果所有筛选条件都为空，直接返回所有文档
        if ((docName == null || docName.trim().isEmpty()) &&
                (nickName == null || nickName.trim().isEmpty()) && (begin == null || begin.trim().isEmpty()) &&
                (end == null || end.trim().isEmpty())) {
            return documentRight;
        }

        for (Map<String, Object> doc : documentRight) {
            boolean shouldInclude = true;

            // 按文档名筛选
            if (docName != null && !docName.trim().isEmpty()) {
                String currentDocName = (String) doc.get("name");
                if (currentDocName == null || !currentDocName.toLowerCase().contains(docName.toLowerCase())) {
                    shouldInclude = false;
                }
            }

            // 按所有者昵称筛选（这里nickName参数实际上是用来筛选所有者的）
            if (shouldInclude && nickName != null && !nickName.trim().isEmpty()) {
                String ownerNickname = (String) doc.get("owner");
                if (ownerNickname == null || !ownerNickname.toLowerCase().contains(nickName.toLowerCase())) {
                    shouldInclude = false;
                }
            }

            // 按时间范围筛选
            if (shouldInclude && (begin != null && !begin.trim().isEmpty() || end != null && !end.trim().isEmpty())) {
                try {
                    String accessTimeStr = (String) doc.get("accessTime");
                    if (accessTimeStr != null && !accessTimeStr.isEmpty()) {
                        Date accessTime = sdf.parse(accessTimeStr);

                        // 开始时间筛选
                        if (begin != null && !begin.trim().isEmpty()) {
                            Date beginDate = sdf.parse(begin);
                            if (accessTime.before(beginDate)) {
                                shouldInclude = false;
                            }
                        }

                        // 结束时间筛选
                        if (shouldInclude && end != null && !end.trim().isEmpty()) {
                            Date endDate = sdf.parse(end);
                            if (accessTime.after(endDate)) {
                                shouldInclude = false;
                            }
                        }
                    }
                } catch (ParseException e) {
                    // 如果日期解析失败，跳过该文档
                    shouldInclude = false;
                }
            }

            if (shouldInclude) {
                filteredDocuments.add(doc);
            }
        }

        return filteredDocuments;
    }

    public Map<String, Object> findByKnowledgeBaseId(Long knowledgeBaseId) {
        List<Document> docs = documentRepository.findByKbId(knowledgeBaseId);
        List<Map<String, Object>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Document doc : docs) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", doc.getDocName());
            map.put("date", doc.getAccessTime() != null ? sdf.format(doc.getAccessTime()) : "");
            map.put("owner", userRepository.findByUserId(doc.getUserId()).getNickname());
            map.put("ownerId", doc.getUserId());
            map.put("id", doc.getDocId());
            map.put("accessTime", sdf.format(doc.getAccessTime()));
            map.put("create_time", sdf.format(doc.getCreateTime()));
            map.put("isCollaborative", doc.getIsCollaborative());
            result.add(map);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        return response;

    }
}
