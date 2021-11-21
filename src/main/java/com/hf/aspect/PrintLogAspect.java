package com.hf.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class PrintLogAspect {

    /**
     * 定义注解切点
     */
    @Pointcut(value = "@annotation(com.hf.annotation.PrintLog)")
    private void printLogPointCut() {
    }

    /**
     * 定义方法级别的切点
     */
    @Pointcut(value = "execution(* com.hf..*(..))")
    private void allPointCut() {}

    @Around(value = "allPointCut()")
    public Object handlerPrintLog(ProceedingJoinPoint joinPoint) {
        // 获取方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        log.info("进入{}方法，参数为{}",methodName, JSONObject.toJSONString(args));

        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String kind = joinPoint.getKind();
        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        Object target = joinPoint.getTarget();
        Object object = null;
        try {
            object  = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("调用{}方法出现错误，{}", methodName, JSONObject.toJSONString(e.getLocalizedMessage()));
        }
        log.info("{}方法处理结束。", methodName);
        return object;
    }
}
