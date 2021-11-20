package com.hf.response;

/**
 * 返回响应码
 */
public enum ResponseCode {

    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败")
    ;

    private Integer code;

    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
