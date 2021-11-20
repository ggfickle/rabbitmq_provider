package com.hf.interceptor;

import com.hf.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.hf.config.SystemConstant.RESPONSE_ANNOTATION;

/**
 * 使用拦截器实现自定义注解包装返回类型
 * 拦截器：拦截请求，判断请求的方法或类上是否使用了自定义的@ResponseResult注解，
 * *               并在请求内设置是否使用了自定义注解的标志位属性；
 */
@Component
@Slf4j
public class ResponseResultInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("进入拦截器..");
        /**
         * Spring MVC应用启动时会搜集并分析每个Web控制器方法，从中提取对应的"<请求匹配条件,控制器方法>“映射关系，形成一个映射关系表保存在一个RequestMappingHandlerMapping bean中。
         * 然后在客户请求到达时，再使用RequestMappingHandlerMapping中的该映射关系表找到相应的控制器方法去处理该请求。
         * 在RequestMappingHandlerMapping中保存的每个”<请求匹配条件,控制器方法>"映射关系对儿中,"请求匹配条件"通过RequestMappingInfo包装和表示，
         * 而"控制器方法"则通过HandlerMethod来包装和表示。
         */
        // 如果请求的方法是Controller的处理方法，则进入
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> aClass = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            // 判断是否在类对象上添加了ResponseResult注解
            if (aClass.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_ANNOTATION, aClass.getAnnotation(ResponseResult.class));
            } else if (method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_ANNOTATION, method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }
}
