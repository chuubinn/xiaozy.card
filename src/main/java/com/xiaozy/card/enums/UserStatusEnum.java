package com.xiaozy.card.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVATE(0,"启用"),
    FORBIDDEN(1,"禁用"),
    ;

    private Integer code;

    private String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
