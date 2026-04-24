package com.f1sh.cloudpicbackend.model.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -124587465083885152L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;
}
