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
     * @param code 自定义错误码
     * @param message 自定义信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 
     * @param errorCode 通用错误码
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     *
     * @param errorCode 通用错误码
     * @param message 详细自定义信息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
