package com.f1sh.cloudpicbackend.common;

import com.f1sh.cloudpicbackend.exception.ErrorCode;

/**
 * 返回响应结果 工具类
 */
public class ResultUtils {

    /**
     * 成功返回
     * @param data 响应数据
     * @return 成功响应，默认状态码code = 20000， 默认消息消息message = "ok"
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(20000, data, "ok");
    }

    /**
     * 错误返回
     * @param errorCode 通用错误码枚举类
     * @param message 自定义错误消息
     * @return 错误响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }
    /**
     * 错误返回
     * @param errorCode 通用错误码枚举类
     * @return 错误响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage());
    }

    /**
     * 错误返回
     * @param code 自定义错误码
     * @param message 自定义错误消息
     * @return 错误响应
     */
    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    } // This method is not used in the code

}
