package com.example.login.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.example.login.service.impl.KBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.entity.ApiResponse;
import com.example.login.entity.Document;
import com.example.login.repository.DocumentRepository;
import com.example.login.repository.UserRepository;
import com.example.login.service.impl.DocumentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/document")
@CrossOrigin(origins = "*")
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentServiceImpl documentServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KBServiceImpl kbServiceImpl;

    // 创建文档
    @PostMapping("/create")
    public ApiResponse<Map<String, Object>> create(@RequestBody Map<String, Object> body) {
        try {
            Document doc = new Document();
            // 设置创建时间和访问时间（北京时间）
            Timestamp now = new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000);
            doc.setCreateTime(now);
            doc.setAccessTime(now);

            doc.setDocName(body.get("docName").toString());
            // Object contentObj = body.get("content");
            // String content = new ObjectMapper().writeValueAsString(contentObj);
            // doc.setContent(content);
            doc.setKbId(Long.parseLong(body.get("kbId").toString()));
            doc.setUserId(Long.parseLong(body.get("userId").toString()));
            doc.setIsCollaborative(Boolean.parseBoolean(body.get("isCollaborative").toString()));
            Document saved = documentRepository.save(doc);

            Map<String, Object> map = new HashMap<>();

            map.put("name", doc.getDocName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("date", doc.getAccessTime() != null ? sdf.format(doc.getAccessTime()) : "");
            map.put("owner", userRepository.findByUserId(doc.getUserId()).getNickname());
            map.put("ownerId", doc.getUserId());
            map.put("id", doc.getDocId());
            map.put("accessTime", sdf.format(doc.getAccessTime()));
            map.put("create_time", sdf.format(doc.getCreateTime()));
            map.put("isCollaborative", doc.getIsCollaborative());

            return new ApiResponse<>(200, "文档创建成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(500, "文档创建失败: " + e.getMessage(), null);
        }
    }

    // 获取文档详情
    @GetMapping("/{id}")
    public ApiResponse<Map<String, Object>> getDocument(@PathVariable Long id) {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) {
            return new ApiResponse<>(404, "文档不存在", null);
        }

        // 更新访问时间（北京时间）
        try {
            doc.setAccessTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
            documentRepository.save(doc);
        } catch (Exception e) {
            // 访问时间更新失败不影响文档获取
            System.err.println("更新访问时间失败: " + e.getMessage());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("doc_id", doc.getDocId());
        data.put("doc_name", doc.getDocName());
        data.put("content", doc.getContent()); // 直接返回JSONObject
        data.put("is_collaborative", doc.getIsCollaborative());
        data.put("user_id", doc.getUserId());
        data.put("kb_id", doc.getKbId());
        return new ApiResponse<>(200, "文档获取成功", data);
    }

    // 查询是否允许协作
    @GetMapping("/{id}/collaboration-status")
    public ApiResponse<Map<String, Object>> getCollaborationStatus(@PathVariable Long id) {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) {
            return new ApiResponse<>(404, "文档不存在", null);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("status", doc.getIsCollaborative());
        return new ApiResponse<>(200, "协作状态获取成功", data);
    }

    // 保存文档内容
    @PostMapping("/{id}/content")
    public ApiResponse<Void> saveContent(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) {
            return new ApiResponse<>(404, "文档不存在", null);
        }

        try {
            // 1. 获取content对象
            Object contentObj = body.get("content");

            // 2. 验证并转换为有效的JSON字符串
            String content;
            if (contentObj == null) {
                content = "null"; // 或者根据需求处理为默认值
            } else if (contentObj instanceof String) {
                // 如果是字符串，验证是否为有效JSON
                try {
                    new ObjectMapper().readTree((String) contentObj);
                    content = (String) contentObj;
                } catch (IOException e) {
                    // 如果不是有效JSON，可以将其作为字符串值包装成JSON
                    content = new ObjectMapper().writeValueAsString(contentObj);
                }
            } else {
                // 如果是Map/List等其他对象，直接序列化为JSON
                content = new ObjectMapper().writeValueAsString(contentObj);
            }

            // 3.设置内容
            doc.setContent(content);
            // 更新访问时间（北京时间）
            doc.setAccessTime(new Timestamp(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
            documentRepository.save(doc);

            return new ApiResponse<>(200, "文档内容保存成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(500, "保存失败: " + e.getMessage(), null);
        }
    }

    // 开启/关闭文档协作
    @PostMapping("/{id}/collaboration")
    public ApiResponse<Void> setCollaboration(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) {
            return new ApiResponse<>(404, "文档不存在", null);
        }
        // 兼容前端传递的布尔值
        Object isCollabObj = body.get("isCollaborative");
        boolean isCollaborative = false;
        if (isCollabObj instanceof Boolean) {
            isCollaborative = (Boolean) isCollabObj;
        } else if (isCollabObj instanceof String) {
            isCollaborative = Boolean.parseBoolean((String) isCollabObj);
        }
        doc.setIsCollaborative(isCollaborative);
        documentRepository.save(doc);
        return new ApiResponse<>(200, "协作状态更新成功", null);
    }

    /**
     * 通过用户ID获取文档列表，返回username、docName、access_time（年月日）
     */
    @GetMapping("/list/{userId}")
    public ApiResponse<Map<String, Object>> getDocumentsByUserId(@PathVariable Long userId) {
        System.out.println("getDocumentsByUserId");
        return new ApiResponse<>(200, "获取文档列表成功", documentServiceImpl.findByUserId(userId));
    }

    @GetMapping("/list")
    public ApiResponse<Map<String, Object>> getDocumentsAll() {
        System.out.println("getDocumentsAll");
        return new ApiResponse<>(200, "获取文档列表成功", documentServiceImpl.findByUserId(null));
    }

    //查询文档
    @PostMapping("/search")
    public ApiResponse<Map<String, Object>> searchDocument(@RequestBody Map<String, Object> body,
            @RequestParam Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("list", documentServiceImpl.searchDocument(
                (String) body.get("docName"),
                (String) body.get("nickName"),
                (String) body.get("begin"),
                (String) body.get("end"), userId));
        return new ApiResponse<>(200, "筛选文档列表成功", response);
    }

    // 删除文档
    @PostMapping("/delete")
    private Map<String, Object> deleteDocumentBydocId(@RequestParam Long docId) {
        return documentServiceImpl.deleteDocumentById(docId);
    }

    // 重命名文档
    @PostMapping("/rename")
    private Map<String, Object> renameDocumentBydocId(@RequestParam Long docId, @RequestParam String newName) {
        return documentServiceImpl.renameDocumentById(docId, newName);
    }

    // 通过知识库查找文档
    @GetMapping("/knowledge-base/{knowledgeBaseId}")
    public ApiResponse<Map<String, Object>> getDocumentsByKnowledgeBaseId(@PathVariable Long knowledgeBaseId) {
        System.out.println("========getDocumentsByKnowledgeBaseId============");
        kbServiceImpl.updateAccessTime(knowledgeBaseId);
        return new ApiResponse<>(200, "根据知识库查询文档成功", documentServiceImpl.findByKnowledgeBaseId(knowledgeBaseId));
    }
}