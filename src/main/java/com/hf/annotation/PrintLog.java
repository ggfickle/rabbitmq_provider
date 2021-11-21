package com.hf.annotation;

import java.lang.annotation.*;

/**
 * 日志打印注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PrintLog {
}
