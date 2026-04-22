package com.f1sh.cloudpicbackend.exception;

import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends RuntimeException{

    private final int code;

    /**
     *
     * @param code 自定义状态码
     * @param message 自定义信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 
     * @param statusCode 通用状态码
     */
    public BusinessException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.code = statusCode.getCode();
    }

    /**
     *
     * @param statusCode 通用状态码
     * @param message 详细自定义信息
     */
    public BusinessException(StatusCode statusCode, String message) {
        super(message);
        this.code = statusCode.getCode();
    }
}
