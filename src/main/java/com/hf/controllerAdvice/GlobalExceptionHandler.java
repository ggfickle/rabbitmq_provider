package com.hf.controllerAdvice;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hf.exception.BizException;
import com.hf.pojo.SystemErrorCode;
import com.hf.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */
@Order(1)
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Object exceptionHandler(BizException e) {
        log.error(e.getMessage());
        return ResponseResult.fail(e.getErrorCode());
    }

    /**
     * 缺少参数抛出的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object exceptionHandler(MissingServletRequestParameterException e) {
        String msg = MessageFormat.format("缺少参数{0}", e.getParameterName());
        log.error(msg);
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    /**
     * get请求的对象参数校验失败后抛出的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Object exceptionHandler(BindException e) {
        log.error("get请求的对象参数校验," + e.getLocalizedMessage());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        List<String> collect = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), JSONObject.toJSONString(collect));
    }

    /**
     * post请求的对象参数校验失败后抛出的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object exceptionHandler(MethodArgumentNotValidException e) {
        log.error("post请求的对象参数校验失败，" + e.getLocalizedMessage());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();

        List<String> collect = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), JSONObject.toJSONString(collect));
    }

    /**
     * 单参数校验失败后抛出的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Object exceptionHandler(ConstraintViolationException e) {
        // 单个参数校验异常
        log.error("单参数校验失败" + e.getLocalizedMessage());
        Set<ConstraintViolation<?>> sets = e.getConstraintViolations();
        List<String> collect = sets.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), JSONObject.toJSONString(collect));
    }

    /**
     * 其余所有的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception e) {
        log.error(e.getLocalizedMessage());
        return ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage());
    }

    /**
     * 空指针异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Object exception(NullPointerException e) {
        Optional<StackTraceElement> first = Arrays.stream(e.getStackTrace()).findFirst();
        return first.<Object>map(stackTraceElement -> {
            log.error("空指针异常：" + stackTraceElement);
            return ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), "空指针异常");
        }).orElseGet(() -> ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), "空指针异常"));
    }
}
