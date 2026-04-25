package com.f1sh.cloudpicbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f1sh.cloudpicbackend.annotation.AuthCheck;
import com.f1sh.cloudpicbackend.common.BaseResponse;
import com.f1sh.cloudpicbackend.common.ResultUtils;
import com.f1sh.cloudpicbackend.constant.UserConstant;
import com.f1sh.cloudpicbackend.exception.StateCode;
import com.f1sh.cloudpicbackend.exception.ThrowUtils;
import com.f1sh.cloudpicbackend.model.dto.*;
import com.f1sh.cloudpicbackend.model.entity.User;
import com.f1sh.cloudpicbackend.model.vo.LoginUserVO;
import com.f1sh.cloudpicbackend.model.vo.UserVO;
import com.f1sh.cloudpicbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 新增用户
     *
     * @param userAddRequest 用户新增请求对象
     * @return 新增用户的id
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        ThrowUtils.throwIf(ObjUtil.isEmpty(userAddRequest), StateCode.PARAMS_ERROR);
        User user = new User();
        BeanUtil.copyProperties(userAddRequest, user);
        String encryptPassword = userService.getEncryptPassword(UserConstant.DEFAULT_USER_PASSWORD);
        user.setUserPassword(encryptPassword);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, StateCode.OPERATE_ERROR, "新增用户失败");
        return ResultUtils.success(user.getId());
    }

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(@RequestParam("id") Long id) {
        ThrowUtils.throwIf((ObjUtil.isEmpty(id) || id <= 0), StateCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(ObjUtil.isEmpty(user), StateCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据id获取用户脱敏信息
     *
     * @param id 用户id
     * @return 用户脱敏信息
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(@RequestParam("id") Long id) {
        BaseResponse<User> userResponse = getUserById(id);
        return ResultUtils.success(userService.getUserVO(userResponse.getData()));
    }

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUserById(@RequestParam("id") Long id) {
        ThrowUtils.throwIf((ObjUtil.isEmpty(id) || id <= 0), StateCode.PARAMS_ERROR);
        boolean result = userService.removeById(id);
        ThrowUtils.throwIf(!result, StateCode.OPERATE_ERROR, "删除用户失败");
        return ResultUtils.success(result);
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest 用户更新请求对象
     * @return 是否成功
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        ThrowUtils.throwIf(ObjUtil.isEmpty(userUpdateRequest), StateCode.PARAMS_ERROR);
        Long id = userUpdateRequest.getId();
        ThrowUtils.throwIf((ObjUtil.isEmpty(id) || id <= 0), StateCode.PARAMS_ERROR);
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, StateCode.OPERATE_ERROR, "更新用户失败");
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, StateCode.PARAMS_ERROR);
        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();
        QueryWrapper<User> queryWrapper = userService.getQueryWrapper(userQueryRequest);
        Page<User> userPage = userService.page(new Page<>(current, pageSize), queryWrapper);
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }
}
