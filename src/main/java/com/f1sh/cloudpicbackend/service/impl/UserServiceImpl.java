package com.f1sh.cloudpicbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f1sh.cloudpicbackend.constant.UserConstant;
import com.f1sh.cloudpicbackend.exception.StateCode;
import com.f1sh.cloudpicbackend.exception.ThrowUtils;
import com.f1sh.cloudpicbackend.model.dto.UserQueryRequest;
import com.f1sh.cloudpicbackend.model.entity.User;
import com.f1sh.cloudpicbackend.model.enums.UserRoleEnum;
import com.f1sh.cloudpicbackend.model.vo.LoginUserVO;
import com.f1sh.cloudpicbackend.model.vo.UserVO;
import com.f1sh.cloudpicbackend.service.UserService;
import com.f1sh.cloudpicbackend.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 28060
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2026-04-24 16:39:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验参数
        ThrowUtils.throwIf(StrUtil.hasBlank(userAccount, userPassword, checkPassword), StateCode.PARAMS_ERROR, "账号与密码不能为空");
        ThrowUtils.throwIf(userAccount.length() < 4, StateCode.PARAMS_ERROR, "账号长度不能小于4");
        ThrowUtils.throwIf(userPassword.length() < 8, StateCode.PARAMS_ERROR, "密码长度不能小于8");
        ThrowUtils.throwIf(!userPassword.equals(checkPassword), StateCode.PARAMS_ERROR, "两次输入的密码不一致");
        // 2. 检查用户是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.baseMapper.selectCount(queryWrapper);
        ThrowUtils.throwIf(count > 0, StateCode.PARAMS_ERROR, "用户 " + userAccount + " 已存在");
        // 3. 加密密码
        String encryptPassword = getEncryptPassword(userPassword);
        // 4. 插入数据到数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean save = this.save(user);
        ThrowUtils.throwIf(!save, StateCode.SYSTEM_ERROR, "注册失败,数据库错误");
        return user.getId();

    }

    @Override
    public String getEncryptPassword(String userPassword) {
        String salt = "F1sh23";
        return DigestUtil.md5Hex(salt + userPassword);
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验参数
        ThrowUtils.throwIf(StrUtil.hasBlank(userAccount, userPassword), StateCode.PARAMS_ERROR, "账号与密码不能为空");
        ThrowUtils.throwIf(userAccount.length() < 4, StateCode.PARAMS_ERROR, "账号长度不能小于4");
        ThrowUtils.throwIf(userPassword.length() < 8, StateCode.PARAMS_ERROR, "密码长度不能小于8");
        // 2. 加密密码
        String encryptPassword = getEncryptPassword(userPassword);
        // 3. 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(ObjUtil.isEmpty(user), StateCode.PARAMS_ERROR, "用户不存在");
        // 4. 记录用户登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return user;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 1. 判断是否已经登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User loginUser = (User) userObj;
        ThrowUtils.throwIf((loginUser == null || loginUser.getId() == null), StateCode.NOT_LOGIN_ERROR);
        // 2. 查询数据库获得最新用户信息
        long userId = loginUser.getId();
        loginUser = this.getById(userId);
        ThrowUtils.throwIf(loginUser == null, StateCode.NOT_LOGIN_ERROR, "用户不存在");
        return loginUser;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 1. 判断是否已经登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User loginUser = (User) userObj;
        ThrowUtils.throwIf((loginUser == null || loginUser.getId() == null), StateCode.NOT_LOGIN_ERROR);
        // 2. 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (ObjUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, StateCode.PARAMS_ERROR, "请求参数不能为空");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        queryWrapper.eq((ObjUtil.isNotNull(id) && id > 0), "id", id);
        queryWrapper.eq(StrUtil.isNotEmpty(userRole), "userRole", userRole);
        queryWrapper.like(StrUtil.isNotBlank(userAccount), "userAccount", userAccount);
        queryWrapper.like(StrUtil.isNotBlank(userName), "userName", userName);
        queryWrapper.like(StrUtil.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;
    }
}




