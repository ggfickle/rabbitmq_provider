package com.hf.response;

import com.hf.base.ErrorCode;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Getter
@Setter
public class ResponseResult<T> implements Serializable {

    /**
     * 请求成功状态
     */
    private Boolean success;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public ResponseResult(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(Boolean success, Integer code, String message) {
        this(success, code, message, null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> fail() {
        return fail(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMessage());
    }

    public static <T> ResponseResult<T> fail(ErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> ResponseResult<T> fail(int code, String message) {
        return new ResponseResult<T>(false, code, message);
    }
}
