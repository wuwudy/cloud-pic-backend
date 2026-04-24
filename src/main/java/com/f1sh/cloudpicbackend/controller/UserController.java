package com.f1sh.cloudpicbackend.controller;

import cn.hutool.core.util.ObjUtil;
import com.f1sh.cloudpicbackend.annotation.AuthCheck;
import com.f1sh.cloudpicbackend.common.BaseResponse;
import com.f1sh.cloudpicbackend.common.ResultUtils;
import com.f1sh.cloudpicbackend.exception.StateCode;
import com.f1sh.cloudpicbackend.exception.ThrowUtils;
import com.f1sh.cloudpicbackend.model.dto.UserLoginRequest;
import com.f1sh.cloudpicbackend.model.dto.UserRegisterRequest;
import com.f1sh.cloudpicbackend.model.entity.User;
import com.f1sh.cloudpicbackend.model.vo.LoginUserVO;
import com.f1sh.cloudpicbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(ObjUtil.isEmpty(userRegisterRequest), StateCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     * @param userLoginRequest 用户登录请求
     * @param request 请求对象
     * @return 脱敏后登录用户信息
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(ObjUtil.isEmpty(userLoginRequest), StateCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User loginUser = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }

    /**
     * 获取登录用户信息
     * @param request 请求对象
     * @return 脱敏后登录用户信息
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }

    /**
     * 用户注销
     * @param request 请求对象
     * @return 是否成功
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }
}
