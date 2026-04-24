package com.f1sh.cloudpicbackend.exception;

import lombok.Getter;

/**
 * 错通用误码枚举类
 */
@Getter
public enum StateCode {

    SUCCESS(20000,"ok"),
    PARAMS_ERROR(40000,"参数错误"),
    NOT_LOGIN_ERROR(40100,"未登录"),
    NO_AUTH_ERROR(40101,"无权限"),
    NOT_FOUND_ERROR(40400,"请求数据不存在"),
    FORBIDDEN_ERROR(43000,"禁止访问"),
    SYSTEM_ERROR(50000,"服务器系统错误"),
    OPERATE_ERROR(50001,"操作错误");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    StateCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
