package com.example.login.repository;

import com.example.login.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "*")
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByUserId(Long userId);
    List<Document> findAll();
    // 更新文档访问时间
    @Modifying
    @Transactional
    @Query(value = "update document set access_time = NOW() where doc_id = ?1", nativeQuery = true)
    void updateAccessedAt(Long id);
}
