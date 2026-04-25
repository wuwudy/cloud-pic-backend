package com.f1sh.cloudpicbackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {
    private static final long serialVersionUID = 1823931253740811354L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色
     */
    private String userRole;
}