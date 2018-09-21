package com.xiaozy.card.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    USERNAME_OR_PASSWORD_ERROR(10,"用户或密码不正确"),
    LOGOUT_SUCCESS(11,"退出成功"),
    ADD_FAIL(12,"添加失败，用户名，手机号，或邮箱已存在"),
 //   ADD_FAIL(13,"添加失败，用户名，手机号，或邮箱已存在"),

    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
