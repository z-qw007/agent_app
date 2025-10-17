package com.zqw.agent_app.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL) // 序列化时忽略值为 null 的字段
public class Result<T> implements Serializable {

    private int code;       // 状态码，对应 ResultCode 的 code
    private String message; // 提示信息，对应 ResultCode 的 message
    private T data;         // 返回的具体数据

    // 默认私有构造函数，强制使用静态工厂方法创建对象
    private Result() {} 

    // ---------------------- 核心 Getter/Setter ----------------------
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    /** 成功返回结果，不带数据 */
    public static <T> Result<T> success() {
        return success(null);
    }

    /** 成功返回结果，携带数据 */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = ResultCode.SUCCESS.getCode();
        result.message = ResultCode.SUCCESS.getMessage();
        result.data = data;
        return result;
    }

    /** 失败返回结果，使用默认失败码 */
    public static <T> Result<T> failed() {
        return failed(ResultCode.FAILED.getMessage());
    }

    /** 失败返回结果，自定义消息 */
    public static <T> Result<T> failed(String message) {
        return failed(ResultCode.FAILED.getCode(), message);
    }
    
    /** 失败返回结果，使用 ResultCode */
    public static <T> Result<T> failed(ResultCode resultCode) {
        return failed(resultCode.getCode(), resultCode.getMessage());
    }

    /** 失败返回结果，自定义状态码和消息 */
    public static <T> Result<T> failed(int code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }
}