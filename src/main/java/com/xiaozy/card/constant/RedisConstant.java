package com.xiaozy.card.constant;

/**
 * reids常量
 */
public interface RedisConstant {

    //token格式
    String TOKEN_PREFIX = "token_%s";

    //过期时间
    Integer EXPIRE = 7200;//2小时
}
