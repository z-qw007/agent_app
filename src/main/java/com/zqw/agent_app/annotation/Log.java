package com.zqw.agent_app.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作描述
     */
    String value() default "";

    /**
     * 是否记录方法参数
     */
    boolean logParams() default true;

    /**
     * 是否记录方法返回值
     */
    boolean logResult() default true;

}
