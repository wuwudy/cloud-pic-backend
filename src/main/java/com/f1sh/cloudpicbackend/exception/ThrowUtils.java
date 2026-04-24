package com.f1sh.cloudpicbackend.exception;

/**
 * 依据条件 抛出异常 工具类
 */
public class ThrowUtils {

    /**
     * 依据条件抛出业务异常
     * @param condition 判断条件
     * @param businessException 业务异常
     */
    public static void throwIf(boolean condition, BusinessException businessException) {
        if (condition) {
            throw businessException;
        }
    }

    /**
     * 依据条件抛出运行时异常
     * @param condition 判断条件
     * @param runtimeException 运行时异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 依据条件抛出带有自定义状态码的、自定义消息的业务异常
     * @param condition 判断条件
     * @param code 自定义状态码
     * @param message 自定义消息
     */
    public static void throwIf(boolean condition, int code, String message) {
        throwIf(condition,new BusinessException(code, message));
    }

    /**
     * 依据条件抛出带有通用状态码的业务异常
     * @param condition 判断条件
     * @param stateCode 通用状态码
     */
    public static void throwIf(boolean condition, StateCode stateCode) {
        throwIf(condition,new BusinessException(stateCode));
    }

    /**
     * 依据条件抛出带有通用状态码的、自定义消息的业务异常
     * @param condition 判断条件
     * @param stateCode 通用状态码
     * @param message 自定义消息
     */
    public static void throwIf(boolean condition, StateCode stateCode, String message) {
        throwIf(condition,new BusinessException(stateCode, message));
    }


}
