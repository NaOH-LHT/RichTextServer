package com.example.login.result;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 * @param <T>
 */

@Data
public class Result<T> implements Serializable {
    private int code;   //1成功，0失败
    private String msg; //返回的信息
    private T data;     //返回的数据

    // 公共的构造函数
    public Result() {
    }

    // 带参数的构造函数，方便快速创建成功或失败的响应
    public Result(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    // 成功响应
    public static <T> Result<T> success(T data) {
        return new Result<>(1,"success",data);
    }

    // 成功响应，带提示信息和数据
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(1, message, data);
    }

    //响应失败
    public static <T> Result<T> fail(String message) {
        return new Result<>(0,message,null);
    }
}
