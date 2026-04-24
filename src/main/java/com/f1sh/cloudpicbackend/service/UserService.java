package com.f1sh.cloudpicbackend.service;

import com.f1sh.cloudpicbackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f1sh.cloudpicbackend.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 28060
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2026-04-24 16:39:13
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 确认密码
     * @return 新用户的id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 获取加密后的密码
     *
     * @param userPassword 用户密码
     * @return 加密后的密码
     */
    String getEncryptPassword(String userPassword);

    /**
     * 用户登录
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 登录后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 登录用户数据脱敏
     * @param user 登录的用户全部信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 根据请求获取当前登录用户
      * @param request 请求对象
      * @return 登录的用户信息
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     * @param request 请求对象
     * @return 是否注销成功
     */
    boolean userLogout(HttpServletRequest request);
}
