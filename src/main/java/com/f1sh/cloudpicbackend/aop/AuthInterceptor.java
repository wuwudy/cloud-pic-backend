package com.f1sh.cloudpicbackend.aop;

import cn.hutool.core.util.ObjUtil;
import com.f1sh.cloudpicbackend.annotation.AuthCheck;
import com.f1sh.cloudpicbackend.exception.StateCode;
import com.f1sh.cloudpicbackend.exception.ThrowUtils;
import com.f1sh.cloudpicbackend.model.entity.User;
import com.f1sh.cloudpicbackend.model.enums.UserRoleEnum;
import com.f1sh.cloudpicbackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthInterceptor {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 权限拦截
     *
     * @param joinPoint 切点
     * @param authCheck 权限检查注解
     * @return Object
     * @throws Throwable 异常
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // 空权限或设置了未定义权限，直接放行
        if (ObjUtil.isEmpty(mustRoleEnum)) {
            return joinPoint.proceed();
        }
        // 获取当前请求
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取当前登录用户权限
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum loginUserRole = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        // 拦截1 ： 需要用户权限，该用户没有任何权限
        ThrowUtils.throwIf(ObjUtil.isEmpty(loginUserRole), StateCode.NO_AUTH_ERROR);
        // 拦截2 ： 需要管理员权限，该用户没有管理员权限
        ThrowUtils.throwIf((UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(loginUserRole)), StateCode.NO_AUTH_ERROR);
        // 通过拦截，放行
        return joinPoint.proceed();
    }
}
