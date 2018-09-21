package com.xiaozy.card.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaozy.card.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    //用户名
    private String userName;

    //手机号
    private String userPhone;

    //用户邮箱
    private String userEmail;

    //用户性别
    private Integer sexy ;

    //用户地区
    private String city;

    //用户状态
    private Integer userStatus;

    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

}
