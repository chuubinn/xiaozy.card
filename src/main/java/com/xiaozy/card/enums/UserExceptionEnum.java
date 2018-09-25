package com.xiaozy.card.enums;

import lombok.Getter;

@Getter
public enum UserExceptionEnum {
    HTTP_500_ERROR(500,"查询出错"),
    ;

    private Integer code;

    private String msg;

    UserExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
