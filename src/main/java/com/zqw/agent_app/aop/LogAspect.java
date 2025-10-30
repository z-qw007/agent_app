package com.zqw.agent_app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.zqw.agent_app.annotation.Log;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 环绕通知：处理带有@Log注解的方法
     */
    @Around("@annotation(Log)")
    public Object logMethod(ProceedingJoinPoint joinPoint, Log Log) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String description = Log.value().isEmpty() ? methodName : Log.value();
        
        long startTime = System.currentTimeMillis();
        
        // 记录方法开始
        if (Log.logParams()) {
            log.info("【{}】开始 | 参数: {}", description, Arrays.toString(joinPoint.getArgs()));
        } else {
            log.info("【{}】开始", description);
        }
        
        try {
            // 执行原方法
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            // 记录方法结束
            if (Log.logResult()) {
                log.info("【{}】完成 | 耗时: {}ms | 结果: {}", description, executionTime, result);
            } else {
                log.info("【{}】完成 | 耗时: {}ms", description, executionTime);
            }
            
            return result;
            
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("【{}】异常 | 耗时: {}ms | 异常信息: {}", description, executionTime, e.getMessage());
            throw e;
        }
    }
}