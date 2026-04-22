package com.f1sh.cloudpicbackend.controller;

import com.f1sh.cloudpicbackend.common.BaseResponse;
import com.f1sh.cloudpicbackend.common.LogUtils;
import com.f1sh.cloudpicbackend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@Slf4j
public class MainController {

    /**
     * 健康检查
     *
     * @return OK
     */
    @GetMapping("/health")
    public BaseResponse<String> health(HttpServletRequest request) {
        LogUtils.infoWithTime("接口调用： /health");
        return ResultUtils.success("OK");
    }
}
