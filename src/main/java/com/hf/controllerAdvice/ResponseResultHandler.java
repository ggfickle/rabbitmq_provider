package com.hf.controllerAdvice;

import com.hf.annotation.ResponseResult;
import com.hf.config.SystemConstant;
import com.hf.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 对 返回响应 进行包装 的增强处理
 */
@Order(2)
@Slf4j
@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    /**
     * true则调用beforeBodyWrite， false则不调用
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        ResponseResult requestAttribute = (ResponseResult) request.getAttribute(SystemConstant.RESPONSE_ANNOTATION);
        return !Objects.isNull(requestAttribute);
    }

    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Method method = returnType.getMethod();
        if (null == method) {
            return false;
        }
        Class<?> returnTypeClass = method.getReturnType();
        if (returnTypeClass.toString().equalsIgnoreCase("void") ) {
            return com.hf.response.ResponseResult.success();
        } else if (responseBody instanceof com.hf.response.ResponseResult) {
            return responseBody;
        } else {
            return com.hf.response.ResponseResult.success(responseBody);
        }
    }
}
