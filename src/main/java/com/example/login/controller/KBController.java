package com.example.login.controller;

import com.example.login.entity.KnowledgeBaseDTO;
import com.example.login.service.KBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth/knowledgeBase")
@CrossOrigin(origins = "*")
public class KBController {

    @Autowired
    private KBService kbService;

    @GetMapping("/list")
    private Map<String, Object> getKnowledgeBaseList(@RequestParam String userName) {
        return kbService.getKnowledgeBaseList(userName);
    }

    @PostMapping("/add")
    private Map<String, Object> addKnowledgeBase(@RequestBody KnowledgeBaseDTO dto) {
        return kbService.addKnowledgeBase(dto);
    }

    // 删除知识库
    @GetMapping("/delete")
    private Map<String, Object> deleteKnowledgeBase(@RequestParam String name){
        return kbService.deleteKnowledgeBase(name);
    }

    // 重命名知识库
    @GetMapping("/rename")
    private Map<String, Object> renameKnowledgeBase(@RequestParam String oldName, @RequestParam String newName){
        return kbService.renameKnowledgeBase(oldName, newName);
    }

    //查询知识库
    @GetMapping("/search")
    private Map<String, Object> queryKnowledgeBase(@RequestParam String name,
                                                   @RequestParam String owner,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate){
        return kbService.queryKnowledgeBase(name, owner, startDate, endDate);
    }
}

