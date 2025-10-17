package com.zqw.agent_app.exception;

import com.zqw.agent_app.common.ResultCode;

/**
 * 自定义业务逻辑异常
 */
public class BusinessException extends RuntimeException {
    private final ResultCode resultCode;

    /**
     * 标准返回
     * @param resultCode 错误码
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
