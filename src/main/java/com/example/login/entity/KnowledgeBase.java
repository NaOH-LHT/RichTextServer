package com.example.login.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "knowledge_base")
public class KnowledgeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kb_id")
    private Long kbId;

    @Column(name = "kb_name")
    private String kbName;

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "create_time")

    private String createTime;

    @Column(name = "access_time")
    private String accessTime;

    @Transient
    private String userName;

    public KnowledgeBase() {
    }


    public KnowledgeBase(Long kbId, String kbName, Long userId, String createTime, String accessTime, String userName) {
        this.kbId = kbId;
        this.kbName = kbName;
        this.userId = userId;
        this.createTime = createTime;
        this.accessTime = accessTime;
        this.userName = userName;
    }

    /**
     * 获取
     * @return kbId
     */
    public Long getKbId() {
        return kbId;
    }

    /**
     * 设置
     * @param kbId
     */
    public void setKbId(Long kbId) {
        this.kbId = kbId;
    }

    /**
     * 获取
     * @return kbName
     */
    public String getKbName() {
        return kbName;
    }

    /**
     * 设置
     * @param kbName
     */
    public void setKbName(String kbName) {
        this.kbName = kbName;
    }

    /**
     * 获取
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     * @return accessTime
     */
    public String getAccessTime() {
        return accessTime;
    }

    /**
     * 设置
     * @param accessTime
     */
    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    /**
     * 获取
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return "KnowledgeBase{kbId = " + kbId + ", kbName = " + kbName + ", userId = " + userId + ", createTime = " + createTime + ", accessTime = " + accessTime + ", userName = " + userName + "}";
    }

}

