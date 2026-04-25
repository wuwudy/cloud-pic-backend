package com.f1sh.cloudpicbackend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 4151020582187520133L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userDescription;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 用户创建时间
     */
    private Date createTime;
}
