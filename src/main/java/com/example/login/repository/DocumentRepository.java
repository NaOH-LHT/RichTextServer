/**
 * DocumentRepository
 * 
 * 用于操作 document 表，提供查询最新文档的方法
 * 
 * @repository
 */
package com.example.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.login.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
  // 查询 access_time 最新的8个文档
  @Query(value = "SELECT * FROM document ORDER BY access_time DESC LIMIT 8", nativeQuery = true)
  List<Document> findTop8ByOrderByAccessTimeDesc();
}
