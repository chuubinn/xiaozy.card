package com.xiaozy.card.form;

import lombok.Data;

@Data
public class UserForm {
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
}
