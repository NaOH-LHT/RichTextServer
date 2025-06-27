/**
 * Document 实体类
 * 
 * 用于映射数据库中的 document 表，包含文档的基本信息和访问时间等字段
 * 
 * @entity
 */
package com.example.login.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "document") // 注意表名为单数
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "doc_id")
  private Long docId;

  @Column(name = "kb_id")
  private String kbId;

  @Column(name = "doc_name")
  private String docName;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "create_time")
  private Date createTime;

  @Column(name = "access_time")
  private Date accessTime;

  @Column(name = "content")
  private String content;

  @Column(name = "is_collaborative")
  private boolean isCollaborative;

  // 必须有无参构造方法
  public Document() {
  }

  // Getter/Setter全部补全
  public Long getDocId() {
    return docId;
  }

  public void setDocId(Long docId) {
    this.docId = docId;
  }

  public String getKbId() {
    return kbId;
  }

  public void setKbId(String kbId) {
    this.kbId = kbId;
  }

  public String getDocName() {
    return docName;
  }

  public void setDocName(String docName) {
    this.docName = docName;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(Date accessTime) {
    this.accessTime = accessTime;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isCollaborative() {
    return isCollaborative;
  }

  public void setCollaborative(boolean isCollaborative) {
    this.isCollaborative = isCollaborative;
  }
}
