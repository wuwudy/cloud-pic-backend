package com.f1sh.cloudpicbackend.common;

import com.f1sh.cloudpicbackend.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础响应
 *
 * @param <T> 响应数据体泛型
 */
@Data
public class BaseResponse<T> implements Serializable {

    // 状态码
    private int code;
    // 响应数据
    private T data;
    // 信息
    private String message;

    /**
     * 全参构造
     * @param code 自定义状态码
     * @param data 响应数据
     * @param message 自定义信息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 缺失message(默认"")构造
     * @param code 自定义状态码
     * @param data 响应数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 通用错误码枚举类构造，响应数据默认null
     * @param errorCode 通用错误码枚举类
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
