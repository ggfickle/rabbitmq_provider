package com.hf.exception;

import com.hf.base.ErrorCode;
import org.slf4j.helpers.MessageFormatter;

public class BizException extends RuntimeException {
    private ErrorCode errorCode;
    private Object[] params;

    public BizException() {
    }

    public BizException(ErrorCode errorCode, Object... objs) {
        super(MessageFormatter.arrayFormat(errorCode.getMessage(), objs).getMessage());
        this.errorCode = errorCode;
        this.params = objs;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public Object[] getParams() {
        return this.params;
    }
}