package com.hf.annotation;

import java.lang.annotation.*;

/**
 * 标记方法返回值需要进行包装的 自定义注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {


}
