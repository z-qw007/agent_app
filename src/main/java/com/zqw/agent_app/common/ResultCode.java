package com.zqw.agent_app.common;

/**
 * 响应结果枚举
 */
public enum ResultCode {
    // 成功
    SUCCESS(200, "操作成功"),

    // 失败 - 客户端错误 (400 - 499)
    VALIDATE_FAILED(400, "参数校验失败"),
    UNAUTHORIZED(401, "暂未登录或Token已过期"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "请求的资源不存在"),

    // 失败 - 业务错误 (600 - 999)
    BUSINESS_ERROR(600, "业务处理失败"),
    EMAIL_CODE_ERROR(601, "邮箱验证码错误或已过期"),
    EMAIL_ALREADY_EXISTS(602, "该邮箱已被注册"),

    // 失败 - 服务器错误 (500 - 599)
    FAILED(500, "操作失败");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
