package com.example.login.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "edit_right")
public class EditRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "right_id")
    private Long rightId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "kb_id")
    private Long kbId;


    public EditRight() {
    }

    public EditRight(Long rightId, Long userId, Long kbId) {
        this.rightId = rightId;
        this.userId = userId;
        this.kbId = kbId;
    }

    /**
     * 获取
     * @return rightId
     */
    public Long getRightId() {
        return rightId;
    }

    /**
     * 设置
     * @param rightId
     */
    public void setRightId(Long rightId) {
        this.rightId = rightId;
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

    public String toString() {
        return "EditRight{rightId = " + rightId + ", userId = " + userId + ", kbId = " + kbId + "}";
    }
}