package com.example.login.entity;

public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    // 成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(1,"success",data);
    }

    // 成功响应，带提示信息和数据
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(1, message, data);
    }

    //响应失败
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(0,message,null);
    }
} 