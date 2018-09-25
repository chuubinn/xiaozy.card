package com.xiaozy.card.enums;

import lombok.Getter;

@Getter

public enum SexyEnum {
    NAN(0,"男"),
    NV(1,"女"),
    ;

    private Integer code;

    private String message;

    SexyEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
}
}
