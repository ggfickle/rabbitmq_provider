package com.hf.pojo;

import com.hf.base.ErrorCode;

/**
 * 系统错误码
 */
public enum SystemErrorCode implements ErrorCode {
    SUCCESS(10001, "SUCCESS"),
    INVOKE_API_ERROR(10002, "调用接口异常。 error:[{}]"),
    UNKNOWN(10003, "未知异常. {}"),
    TOKEN_EXPIRED(10004, "token 已失效！"),
    INVALID_ARGUMENT(10005, "无效参数. {}"),
    ILLEGAL_OPERATION(10006, "无权限操作"),
    USER_TOKEN_INVALID(10007, "登录超时,请重新登录"),
    USER_NOT_EXIST(10008, "{} 帐号不存在"),
    CURRENT_USER_NOT_EXIST(10009, "当前用户信息不存在");

    private final Integer code ;
    private final String message ;

    SystemErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
