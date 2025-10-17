package com.zqw.agent_app.exception;

import com.zqw.agent_app.common.Result;
import com.zqw.agent_app.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常处理: {}", e);
        return Result.failed(e.getResultCode().getCode(), e.getMessage());
    }

}
