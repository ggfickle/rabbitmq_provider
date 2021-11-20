package com.hf.controllerAdvice;

import com.hf.annotation.ResponseResult;
import com.hf.config.SystemConstant;
import com.hf.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 对 返回响应 进行包装 的增强处理
 */
@Slf4j
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ResponseResult requestAttribute = (ResponseResult)request.getAttribute(SystemConstant.RESPONSE_ANNOTATION);
        return !Objects.isNull(requestAttribute);
    }

    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("返回的实体类包装中...");
        com.hf.response.ResponseResult<Object> result;
        if (responseBody instanceof Boolean) {
            // boolean类型时判断一些数据库新增、更新、删除的操作是否成功
            if ((Boolean) responseBody) {
                result = new com.hf.response.ResponseResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), responseBody);
            } else {
                result = new com.hf.response.ResponseResult<>(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMessage(), responseBody);
            }
        } else {
            // 判断像查询一些返回数据的情况，查询不到数据返回 null;
            if (null != responseBody) {
                result = new com.hf.response.ResponseResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), responseBody);
            } else {
                result = new com.hf.response.ResponseResult<>(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMessage(), null);
            }
        }
        return result;
    }
}
