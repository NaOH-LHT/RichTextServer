package com.example.login.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private Long docId;

    @Column(name = "kb_id")
    private Long kbId;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "access_time")
    private Timestamp accessTime;

    @Column(name = "content", columnDefinition = "JSON")
    private String content;

    @Column(name = "is_collaborative")
    private Boolean isCollaborative;

    public Document(){};
    public Document(Long docId,Long kbId,String docName,Long userId,Timestamp createTime,Timestamp accessTime,String content,Boolean isCollaborative){
        this.docId = docId;
        this.kbId = kbId;
        this.docName = docName;
        this.userId = userId;
        this.createTime = createTime;
        this.accessTime = accessTime;
        this.content = content;
        this.isCollaborative = isCollaborative;
    }

    // Getters and Setters
    public Long getDocId() { return docId; }
    public void setDocId(Long docId) { this.docId = docId; }
    public Long getKbId() { return kbId; }
    public void setKbId(Long kbId) { this.kbId = kbId; }
    public String getDocName() { return docName; }
    public void setDocName(String docName) { this.docName = docName; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }
    public Timestamp getAccessTime() { return accessTime; }
    public void setAccessTime(Timestamp accessTime) { this.accessTime = accessTime; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Boolean getIsCollaborative() { return isCollaborative; }
    public void setIsCollaborative(Boolean isCollaborative) { this.isCollaborative = isCollaborative; }
}
