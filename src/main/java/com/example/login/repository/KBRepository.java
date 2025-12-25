package com.example.login.repository;

import com.example.login.entity.KnowledgeBase;
import com.example.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "*")
public interface KBRepository extends JpaRepository<KnowledgeBase, Long> {

    //根据用户id查询知识库
    List<KnowledgeBase> findByUserId(Long userId);

    //根据知识库id查询知识库
    KnowledgeBase findByKbId(Long kbId);

    //根据知识库名称查询知识库
    KnowledgeBase findByKbName(String kbName);

    //根据知识库名称模糊查询知识库
    List<KnowledgeBase> findByKbNameContaining(String name);
}
